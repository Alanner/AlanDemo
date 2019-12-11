<template>
  <section style="background: white">
    <br><br>
    <div class="ui segment" id="center">
      <div class="ui grid">
        <div class="two wide column"></div>
        <div class="twelve wide column" style="z-index: 2;">
          <br><br><br><br>
          <div class="ui clearing segment" id="taskDes">
            <a id="TaskName" class="ui text">{{task.taskname}}</a><br><br>
            <a id="publisher" class="ui right floated label">发布者：{{task.taskpublishername}}</a>
            <a id="time" class="ui right floated label">时间：{{parseInt(task.time) | formatDate}}</a>
            <a id="importance" class="ui right floated red label">重要</a>
            <a id="status" class="ui right floated red label">{{task.taskstatus === 0 ? '未截止': "已截止"}}</a>
          </div>
          <div class="ui segment" id="taskDetail">
            <a class="ui label">具体：</a><br>
            <div class="ui segment" id="detail">
              {{task.taskcontent}}
            </div>
          </div>
          <div class="ui segment" id="MemberStatus">
            <a class="ui label">查看成员完成情况：</a>
            <a class="ui green label">{{task.userdone.length}}/{{group.userlist.length}}</a>
            <a class="ui right floated tiny button" @click="open">{{btnText}}</a>
            <div class="ui divider"></div>
            <div class="ui container" v-show="show">
              <div class="ui grid">
                <div class="two wide column" v-for="(finisher,index) of users" :key="index">
                  <img class="ui mini left floated image" :src="finisher.avatar" >
                  <a class="ui label red" v-bind:class="{green:task.userdone.indexOf(parseInt(finisher.id)) !== -1}"></a><br>
                  <a>{{finisher.username}}</a>
                </div>
              </div>
            </div>
          </div>
          <SubmitFile v-show="submitFile" @cancel="submitFile = false" :taskid="task.id" @successMethod="successMethod" @failMethod="submitFailShow = true" :taskname="task.fileformat"></SubmitFile>
          <ErrorInfo v-show="submitSuccessShow" @cancel="submitSuccessShow = false" :msg="submitSuccessMsg" :confirm="false" :hasMethod="true" @method="refreshData"></ErrorInfo>
          <ErrorInfo v-show="submitFailShow" @cancel="submitFailShow = false" :msg="submitFailMsg" :confirm="false"></ErrorInfo>
          <button class="ui green right floated button" @click="submitFile = true">提交，确认收到通知</button>
          <button class="ui green right floated button" @click="back">返回</button>
          <br><br><br><br><br><br>
        </div>
        <div class="two wide column"></div>
      </div>
    </div>
  </section>
</template>

<script>
import SubmitFile from '@/components/SubmitFile'
import ErrorInfo from '@/components/ErrorInfo'
import {formatDate} from '../../static/js/date.js'
export default {
  name: 'Task',
  data: function () {
    return {
      show: false,
      submitFile: false,
      btnText: '展开',
      submitSuccessShow: false,
      submitSuccessMsg: '提交成功！',
      submitFailShow: false,
      submitFailMsg: '提交失败！',
      task: {
        userdone: {}
      },
      group: {
        userlist: {}
      },
      users: {}
    }
  },
  methods: {
    open: function () {
      if (this.show) {
        this.show = false
        this.btnText = '展开'
      } else {
        this.show = true
        this.btnText = '收起'
      }
    },
    successMethod: function () {
      this.submitSuccessShow = true
    },
    back: function () {
      this.$router.go(-1)
    },
    refreshData: function () {
      var url = '/api/Task/gettask'
      this.axios.get(url, {
        params: {
          taskid: this.$route.params.id
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            this.task = data.task
            url = '/api/group/getGroup'
            this.axios.get(url, {
              params: {
                groupId: data.task.groupid
              }
            })
              .then((response) => {
                var data = response.data
                if (data.status === 1) {
                  console.log(data)
                  this.group = data.groupInfo
                  this.users = data.members
                } else {
                  this.$router.push({path: '/'})
                }
              })
              .catch(function (error) {
                console.log(error)
              })
          } else {
            this.$router.push({path: '/'})
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  },
  components: {
    SubmitFile,
    ErrorInfo
  },
  mounted: function () {
    var url = '/api/Task/gettask'
    this.axios.get(url, {
      params: {
        taskid: this.$route.params.id
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          this.task = data.task
          url = '/api/group/getGroup'
          this.axios.get(url, {
            params: {
              groupId: data.task.groupid
            }
          })
            .then((response) => {
              var data = response.data
              if (data.status === 1) {
                console.log(data)
                this.group = data.groupInfo
                this.users = data.members
              } else {
                this.$router.push({path: '/'})
              }
            })
            .catch(function (error) {
              console.log(error)
            })
        } else {
          this.$router.push({path: '/'})
        }
      })
      .catch(function (error) {
        console.log(error)
      })
  },
  beforeCreate: function () {
    if (sessionStorage.getItem('user') === 'undefined' || sessionStorage.getItem('user') === 'null') {
      this.$router.push({path: '/'})
    }
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
