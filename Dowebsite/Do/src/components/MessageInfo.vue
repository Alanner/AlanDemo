<template>
  <div>
    <div class="ui comments" style="margin: 0">
      <div class="comment">
        <a class="avatar">
          <img class="ui mini image" :src="sender.avatar">
        </a>
        <div class="content">
          <a class="author">{{sender.username}}</a>
          <div class="metadata">
            <div class="date">{{parseInt(message.time) | formatDate}}</div>
          </div>
          <div class="text">
            {{message.text}}
          </div>
        </div>
      </div>
    </div>
    <div class="ui compact segments" style="width: 100%;margin: 0">
      <div class="ui green segment">
        <i class="red circle icon" :class="{green:this.message.notificationstatus === 1 || this.message.notificationstatus === 2}"></i>
        <a href="#" name="Title">邀请你加入群组</a>
        <button class="ui blue mini right floated button" @click="checkInfo">{{checkText}}</button>
      </div>
      <div class="ui segment" v-show="show" >
        <p style="color: black">{{message.notifictioncontent}}</p>
        <button style="margin: 5px" class="ui blue mini right floated button" @click="join" v-show="this.message.notificationstatus === 0 || this.message.notificationstatus === 1">确认加入</button>
        <button style="margin: 5px" class="ui blue mini right floated button" @click="comfirmInfo" v-show="this.message.notificationstatus === 0">标记已阅</button>
      </div>
    </div>
  </div>
</template>

<script>
import {formatDate} from '../../static/js/date.js'
export default {
  name: 'MessageInfo',
  data: function () {
    return {
      show: false,
      checkText: '查看详细',
      sender: {
        avatar: 'http://pf8r5ullw.bkt.clouddn.com/-1257351316062730504',
        username: '舒鑫'
      }
    }
  },
  props: ['message'],
  methods: {
    comfirmInfo: function () {
      var url = '/api/Notification/changestatus'
      this.axios.get(url, {
        params: {
          StudentId: JSON.parse(sessionStorage.getItem('user')).studentid,
          notificationid: this.message.id
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            this.$emit('method')
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
    },
    join: function () {
      var url = '/api/group/adduser'
      this.axios.get(url, {
        params: {
          studentId: JSON.parse(sessionStorage.getItem('user')).studentid,
          groupid: this.message.groupid
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            var url = '/api/Notification/changestatustwo'
            this.axios.get(url, {
              params: {
                StudentId: JSON.parse(sessionStorage.getItem('user')).studentid,
                notificationid: this.message.id
              }
            })
              .then((response) => {
                var data = response.data
                console.log(data)
                if (data.status === 1) {
                  this.$emit('method')
                }
              })
              .catch((error) => {
                console.log(error)
              })
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  },
  created: function () {
    var url = '/api/user/getbaseuser'
    this.axios.get(url, {
      params: {
        StudenId: this.message.senderId
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          console.log(data)
          this.sender = data.user
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
