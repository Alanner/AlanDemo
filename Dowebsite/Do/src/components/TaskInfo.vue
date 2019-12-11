<template>
  <div>
    <div class="ui comments" style="margin: 0">
      <div class="comment">
        <a class="avatar">
          <img class="ui mini image" :src="img">
        </a>
        <div class="content">
          <a class="author">{{task.taskpublishername}}</a>
          <div class="metadata">
            <div class="date">{{parseInt(task.time) | formatDate}}</div>
          </div>
          <div class="text">
            {{task.tasktext}}
          </div>
        </div>
      </div>
    </div>
    <div class="ui compact segments" style="width: 100%;margin: 0">
      <div class="ui green segment">
        <i class="red circle icon" :class="{green:task.userdone.indexOf(parseInt(id)) !== -1}"></i>
        <a href="#" name="Title">{{task.taskname}}</a>
        <button class="ui blue mini right floated button" @click="checkInfo">{{info}}</button>
      </div>
      <div class="ui segment" v-if="show">
        <div>
          <p style="color: black">{{task.taskcontent}}</p>
        </div>
        <button @click="submitFile" class="ui blue mini right floated button">提交文件</button>
      </div>
    </div>
  </div>
</template>

<script>
import {formatDate} from '../../static/js/date.js'
export default {
  name: 'TaskInfo',
  data: function () {
    return {
      show: false,
      info: '查看详细',
      user: {},
      img: ''
    }
  },
  props: ['task', 'id'],
  methods: {
    submitFile: function () {
      this.$router.push({path: '/Task/' + this.task.id})
    },
    checkInfo: function () {
      if (this.show) {
        this.show = false
        this.info = '查看详细'
      } else {
        this.show = true
        this.info = '收起详细'
      }
    }
  },
  created: function () {
    var url = '/api/user/getbaseuser'
    this.axios.get(url, {
      params: {
        StudenId: this.task.taskpublisher
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
