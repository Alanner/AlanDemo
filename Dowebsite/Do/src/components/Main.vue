<template>
  <section id="intro" style="background: white">
    <br><br>
    <div class="ui segment" id="center">
      <div class="ui grid">
        <div class="two wide column"></div>
        <div class="twelve wide column">
          <br>
          <div class="ui segment">
            <div class="ui grid">
              <div class="five wide column" style="z-index: 2;">
                <div class="ui clearing segment" >
                  <div>
                    <a style="cursor: pointer;" @click="changePersonalTitle = true" title="修改头像"><img  class="ui tiny left floated image" :src="avatar"></a>
                    <a id="Username">{{username}}</a><br>
                    <a id="Useremail">{{useremail}}</a><br><br>
                    <button class="ui right floated button" style="font-size: 10px" @click="changePassword = true">
                      修改密码</button>
                  </div>
                </div>
                <div class="ui clearing segment" >
                  <div v-show="this.tasks.length !== 0">
                    <i class="red circle icon"  style="float: left;margin: 5px"></i>
                    <a style="float: left;margin: 5px"  href="#" @click="currentTab = tabs[1]">{{taskInfo}}</a>
                  </div>
                  <div v-show="this.tasks.length === 0">
                    <i class="green circle icon" style="float: left;margin: 5px"></i>
                    <a  style="float: left;margin: 5px"  href="#" @click="currentTab = tabs[1]">{{taskInfo}}</a>
                  </div>
                </div>
                <div id="UserGroup" class="ui clearing segment" >
                  <div class="ui clearing segment" style="padding: 5px;margin: 0">
                    <a style="margin: 10px;">所有群组</a>
                    <button class="ui mini green right floated button" style="margin: 5px" @click="createGroup = true" v-on:successMethod="createGroupSuccessShow = true" v-on:failMethod="createGroupFailShow = true">创建群组</button>
                  </div>
                  <br>
                  <div style="overflow:auto;height: 350px">
                  <div class="ui clearing segment" v-for="(group,index) of groups" :key="index" style="margin: 5px" v-show="groups.length !== 0">
                      <div >
                          <img  class="ui mini left floated image" :src="group.groupavatar">
                          <router-link :to="'/Group/'+group.id">{{group.groupname}}</router-link><br>
                          <a >{{group.groupgescription}}</a>
                      </div>
                  </div>
                   <a v-show="groups.length === 0"> 暂无群组</a>
                  </div>
                </div>
              </div>
              <div class="eleven wide column" style="z-index: 2;">
                <div class="ui top attached tabular menu">
                  <a v-for="(tab,index) of tabs" :key="index" class="item" :class="{active: currentTab.name === tab.name}" v-on:click="currentTab = tab" data-tab="first">{{tab.name}}</a>
                </div>
                <br>
                <transition name="fade">
                  <keep-alive>
                    <component :is="currentTab.component" :info="currentTab.data" :id="userid" @method="refreshPersonalInfo"></component>
                  </keep-alive>
                </transition>
              </div>
            </div>
          </div>
        </div>
        <div class="two wide column "></div>
      </div>
      <CreateGroup v-show="createGroup" @cancel="createGroup = false"  v-on:successMethod="createGroupSuccessShow = true" v-on:failMethod="createGroupFailShow = true"></CreateGroup>
      <ChangePassword v-show="changePassword" @cancel="changePassword = false" v-on:successMethod="changePWDSuccessShow = true" v-on:failMethod="changePWDFailShow = true"></ChangePassword>
      <ChangPersonalTitle :img="avatar" v-show="changePersonalTitle" @cancel="changePersonalTitle = false" @successMethod="changeTitleSuccessShow = true" @failMethod="changeTitleFailShow = true"></ChangPersonalTitle>
      <ErrorInfo v-show="changePWDFailShow" v-on:cancel="changePWDFailShow = false" :msg="changePWDFailMsg" :confirm="false"></ErrorInfo>
      <ErrorInfo v-show="changePWDSuccessShow" v-on:cancel="changePWDSuccessShow = false" :msg="changePWDSuccessMsg" :confirm="false" :hasMethod="true" @method="toLogin"></ErrorInfo>
      <ErrorInfo v-show="createGroupFailShow" v-on:cancel="createGroupFailShow = false" :msg="createGroupFailMsg" :confirm="false"></ErrorInfo>
      <ErrorInfo v-show="createGroupSuccessShow" v-on:cancel="createGroupSuccessShow = false" :msg="createGroupSuccessMsg" :confirm="false" :hasMethod="true" @method="refreshPersonalInfo"></ErrorInfo>
      <ErrorInfo v-show="changeTitleFailShow" v-on:cancel="changeTitleFailShow = false" :msg="changeTitleFailMsg" :confirm="false"></ErrorInfo>
      <ErrorInfo v-show="changeTitleSuccessShow" v-on:cancel="changeTitleSuccessShow = false" :msg="changeTitleSuccessMsg" :confirm="false" :hasMethod="true" @method="refreshPersonalInfo"></ErrorInfo>
    </div>
  </section>
</template>

<script>
import Notice from '@/components/Notice'
import CheckTask from '@/components/CheckTask'
import Message from '@/components/Message'
import CreateGroup from '@/components/CreateGroup'
import ChangePassword from '@/components/ChangePassword'
import ChangPersonalTitle from '@/components/ChangPersonalTitle'
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
  },
  {
    name: '所有通知',
    component: Message,
    data: []
  }
]

export default {
  name: 'Main',
  data: function () {
    return {
      username: '',
      useremail: '1025565665@qq.com',
      avatar: '',
      userid: '11111111',
      groups: [],
      tabs: tabs,
      currentTab: tabs[0],
      createGroup: false,
      changePassword: false,
      changePersonalTitle: false,
      changePWDFailShow: false,
      changePWDSuccessShow: false,
      changePWDFailMsg: '修改密码失败',
      changePWDSuccessMsg: '修改密码成功，请重新登录',
      createGroupFailMsg: '创建失败！',
      createGroupSuccessMsg: '创建成功！',
      createGroupFailShow: false,
      createGroupSuccessShow: false,
      tasks: [],
      taskIndex: 0,
      taskInfo: '暂无任务',
      changeTitleFailShow: false,
      changeTitleFailMsg: '头像更新失败！',
      changeTitleSuccessShow: false,
      changeTitleSuccessMsg: '头像更新成功！'
    }
  },
  methods: {
    taskChange: function () {
      this.taskIndex++
      if (this.taskIndex > this.tasks.length - 1) {
        this.taskIndex = 0
      }
    },
    toLogin: function () {
      this.$router.push({path: '/'})
    },
    refreshPersonalInfo: function () {
      var url = '/api/user/getUser'
      this.axios.get(url, {
        params: {
          StudentId: JSON.parse(sessionStorage.getItem('user')).studentid
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.groups = data.grouplist
            this.tasks = data.tasklist
            tabs[0].data = data.messagelist
            tabs[1].data = data.tasklist
            tabs[2].data = data.notificationlist
          } else {
            this.$router.push({path: '/'})
          }
        })
        .catch(function (error) {
          console.log(error)
        })

      url = '/api/user/getbaseuser'
      this.axios.get(url, {
        params: {
          StudenId: JSON.parse(sessionStorage.getItem('user')).studentid
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.username = data.user.username
            this.useremail = data.user.studentid + '@csu.edu.cn'
            this.userid = data.user.id
            this.avatar = data.user.avatar
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
    CreateGroup,
    ChangePassword,
    ChangPersonalTitle,
    ErrorInfo
  },
  mounted: function () {
    var url = '/api/user/getUser'
    this.axios.get(url, {
      params: {
        StudentId: JSON.parse(sessionStorage.getItem('user')).studentid
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          console.log(data)
          this.groups = data.grouplist
          this.tasks = data.tasklist
          tabs[0].data = data.messagelist
          tabs[1].data = data.tasklist
          tabs[2].data = data.notificationlist
          if (this.tasks.length !== 0) {
            setInterval(this.taskChange, 1000)
            this.taskInfo = '待完成'
          } else {
            this.taskInfo = '暂无任务'
          }
        } else {
          this.$router.push({path: '/'})
        }
      })
      .catch(function (error) {
        console.log(error)
      })

    url = '/api/user/getbaseuser'
    this.axios.get(url, {
      params: {
        StudenId: JSON.parse(sessionStorage.getItem('user')).studentid
      }
    })
      .then((response) => {
        var data = response.data
        console.log(data)
        if (data.status === 1) {
          console.log(data)
          this.username = data.user.username
          this.useremail = data.user.studentid + '@csu.edu.cn'
          this.userid = data.user.id
          this.avatar = data.user.avatar
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
  }
}
</script>

<style>
  .fade-enter-active {
    transition: all .5s
  }
  .fade-enter, .fade-leave-active {
    opacity: 0;
    transform: rotate( -70deg ) translateX( 300px );
  }
</style>
