<template>
  <div>
    <div class="ui comments" style="margin: 0">
      <div class="comment">
        <a class="avatar">
          <img class="ui mini image" :src="img">
        </a>
        <div class="content">
          <a class="author">{{notice.messagepublishername}}</a>
          <div class="metadata">
            <div class="date">{{parseInt(notice.time) | formatDate}}</div>
          </div>
          <div class="text">
            {{notice.messageabstract}}
          </div>
        </div>
      </div>
    </div>
    <div class="ui compact segments" style="width: 100%;margin: 0">
      <div class="ui green segment">
        <i class="red circle icon" :class="{green:notice.userreaded.indexOf(parseInt(id)) !== -1}"></i>
        <a href="#" name="Title">{{notice.messagename}}</a>
        <button class="ui blue mini right floated button" @click="checkInfo">{{checkText}}</button>
      </div>
      <div class="ui segment" v-show="show">
        <div>
          <p style="color: black">{{notice.messagecontent}}</p>
        </div>
        <button class="ui blue mini right floated button" @click="comfirmInfo" v-show="comfirmShow">确认已阅</button>
      </div>
    </div>
  </div>
</template>

<script>
import {formatDate} from '../../static/js/date.js'
export default {
  name: 'NoticeInfo',
  data: function () {
    return {
      show: false,
      checkText: '查看详细',
      comfirmShow: this.notice.userreaded.indexOf(parseInt(this.id)) === -1,
      img: ''
    }
  },
  props: ['notice', 'id'],
  methods: {
    comfirmInfo: function () {
      var url = '/api/Message/changestatus'
      this.axios.get(url, {
        params: {
          StudentId: this.id,
          messageid: this.notice.id
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            this.$emit('method')
            this.comfirmShow = false
          } else {
            alert('shibai')
          }
        })
        .catch((error) => {
          console.log(error)
        })
    },
    checkInfo: function () {
      if (this.show) {
        this.show = false
        this.checkText = '查看详细'
      } else {
        this.show = true
        this.checkText = '收起详细'
      }
    }
  },
  created: function () {
    var url = '/api/user/getbaseuser'
    this.axios.get(url, {
      params: {
        StudenId: this.notice.messagepublisher
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          console.log(data)
          this.img = data.user.avatar
        } else {
          this.$router.push({path: '/'})
        }
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  filters: {
    formatDate (time) {
      let date = new Date(time)
      return formatDate(date, 'yyyy-MM-dd hh:mm')
    }
  }
}
</script>

<style scoped>

</style>
