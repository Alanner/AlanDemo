<template>
  <section id="intro" style="background: white">
    <br><br>
    <div class="ui segment" id="center">
      <div class="ui grid">
        <div class="two wide column "></div>
        <div class="twelve wide column">
          <br>
          <div class="ui segment">
            <div class="ui grid">
              <div class="five wide column" style="z-index: 2;">
                <div class="ui clearing segment" >
                  <div id="UserInfo" >
                    <img id="GourpImg" class="ui tiny left floated image" :src="group.groupavatar">
                    <a id="Groupname">{{group.groupname}}</a><br>
                    <a id="GroupDsb">{{group.groupgescription}}</a><br><br>
                    <button class="ui right floated button" style="font-size: 10px" @click="retreatGroup = true">
                      退出群组</button>
                  </div>
                </div>
                <div id="UserGroup" class="ui clearing segment">
                  <div class="ui clearing segment" style="padding: 5px;margin: 0">
                    <button class="ui mini green left floated button" style="margin: 7px" @click="sendNotice = true">发送通知</button>
                    <button class="ui mini green left floated button" style="margin: 7px" @click="createTask = true">发布任务</button>
                    <button class="ui mini green null floated button" style="margin: 7px" @click="invite = true">邀请加入</button>
                  </div>
                  <br>
                  <div style="overflow:auto;height: 400px">
                  <div class="ui clearing segment" id="MemberInfo" v-for="(groupmate,index) of members" :key="index" style="margin: 5px">
                      <img  class="ui mini left floated image" :src="groupmate.avatar">
                      <a href="#">{{groupmate.username}}</a><br>
                      <a >{{groupmate.studentid}}</a>
                  </div>
                  </div>
                </div>
              </div>

              <div class="eleven wide column" style="z-index:2;">
                <div class="ui top attached tabular menu">
                  <a v-for="(tab,index) of tabs" :key="index" class="item" :class="{active: currentTab.name === tab.name}" v-on:click="currentTab = tab" data-tab="first">{{tab.name}}</a>
                </div>
                <br>
                <transition name="fade">
                  <keep-alive>
                    <component :is="currentTab.component" :info="currentTab.data" :id="userid" @method="refreshData"></component>
                  </keep-alive>
                </transition>
            </div>
          </div>
        </div>
        <div class="two wide column ">
        </div>
      </div>
    </div>
    </div>
    <SendNotice v-show="sendNotice" @cancel="sendNotice = false" :groupId="this.$route.params.id" @successMethod="sendNoticeSuccess = true" @failMethod="sendNoticeFail = true"></SendNotice>
    <CreateTask v-show="createTask" @cancel="createTask = false" :groupId="this.$route.params.id" @successMethod="createTaskSuccess = true" @failMethod="createTaskFail = true"></CreateTask>
    <Invite v-show="invite" @cancel="invite = false" :groupid="group.id" :userstudentid="userstudentid"></Invite>
    <ErrorInfo v-show="retreatGroup" @cancel="retreatGroup = false" :msg="msg" :hasMethod="true" @method="retreatGroupMethod" :confirm="true"></ErrorInfo>
    <ErrorInfo v-show="retreatGroupSuccess" @cancel="retreatGroupSuccess = false" :msg="retreatGroupSuccessMsg" :hasMethod="true" @method="toPersonal"></ErrorInfo>
    <ErrorInfo v-show="retreatGroupFail" @cancel="retreatGroupFail = false" :msg="retreatGroupFailMsg" :confirm="false"></ErrorInfo>
    <ErrorInfo v-show="sendNoticeSuccess" @cancel="sendNoticeSuccess = false" :msg="sendNoticeSuccessMsg" :hasMethod="true" @method="refreshData"></ErrorInfo>
    <ErrorInfo v-show="sendNoticeFail" @cancel="sendNoticeFail = false" :msg="sendNoticeFailMsg" :confirm="false"></ErrorInfo>
    <ErrorInfo v-show="createTaskSuccess" @cancel="createTaskSuccess = false" :msg="createTaskSuccessMsg" :hasMethod="true" @method="refreshData"></ErrorInfo>
    <ErrorInfo v-show="createTaskFail" @cancel="createTaskFail = false" :msg="createTaskFailMsg" :confirm="false"></ErrorInfo>
  </section>
</template>

<script>
import Notice from '@/components/Notice'
import CheckTask from '@/components/CheckTask'
import SendNotice from '@/components/SendNotice'
import CreateTask from '@/components/CreateTask'
import Invite from '@/components/Invite'
import ErrorInfo from '@/components/ErrorInfo'

var tabs = [
  {
    name: '所有公告',
    component: Notice,
    data: []
  },
  {
    name: '所有任务',
    component: CheckTask,
    data: []
  }
]
export default {
  name: 'Group',
  data: function () {
    return {
      msg: '退出当前群聊？',
      group: {
        groupname: '中南大学'
      },
      userid: '',
      members: [],
      tabs: tabs,
      currentTab: tabs[0],
      sendNotice: false,
      createTask: false,
      invite: false,
      retreatGroup: false,
      sendNoticeSuccess: false,
      sendNoticeSuccessMsg: '发布通知成功！',
      sendNoticeFail: false,
      sendNoticeFailMsg: '发布通知失败！',
      retreatGroupSuccess: false,
      retreatGroupSuccessMsg: '退出成功',
      retreatGroupFail: false,
      retreatGroupFailMsg: '退出失败',
      createTaskSuccess: false,
      createTaskSuccessMsg: '发布任务成功！',
      createTaskFail: false,
      createTaskFailMsg: '发布任务失败！',
      userstudentid: ''
    }
  },
  methods: {
    retreatGroupMethod: function () {
      var url = '/api/group/deleteuser'
      this.axios.get(url, {
        params: {
          studentId: JSON.parse(sessionStorage.getItem('user')).studentid,
          groupId: this.$route.params.id
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.retreatGroupSuccess = true
          } else {
            this.retreatGroupFail = true
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    toPersonal: function () {
      this.$router.push({path: '/Main'})
    },
    refreshData: function () {
      var url = '/api/group/getGroup'
      this.axios.get(url, {
        params: {
          groupId: this.$route.params.id
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            this.group = data.groupInfo
            this.members = data.members
            tabs[0].data = data.notices
            tabs[1].data = data.tasks
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
    SendNotice,
    CreateTask,
    Invite,
    ErrorInfo
  },
  created: function () {
    var url = '/api/group/getGroup'
    this.axios.get(url, {
      params: {
        groupId: this.$route.params.id
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          this.group = data.groupInfo
          this.members = data.members
          tabs[0].data = data.notices
          tabs[1].data = data.tasks
        } else {
          // this.$router.push({path: '/'})
        }
      })
      .catch(function (error) {
        console.log(error)
      })
    if (sessionStorage.getItem('user') === 'undefined' || sessionStorage.getItem('user') === 'null') {
      this.$router.push({path: '/'})
    } else {
      this.userid = JSON.parse(sessionStorage.getItem('user')).id
      this.userstudentid = JSON.parse(sessionStorage.getItem('user')).studentid
    }
  }
}
</script>

<style scoped>
  .fade-enter-active {
    transition: all .5s
  }
  .fade-enter, .fade-leave-active {
    opacity: 0;
    transform: rotate( -70deg ) translateX( 300px );
  }
</style>
