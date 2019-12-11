<template>
  <section id="intro" style="background: white;">
    <div class="intro-text" style="z-index: 2;">
      <br/><br/><br/><br/>
      <h2 class="ui header" style="color: black" >欢迎使用Do</h2>
      <p style="color:black ">帮助你发布消息，查看消息，发布任务完成任务</p>
      <div class="ui segment" style="width: 60%">
        <div class="ui container">
          <div class="ui grid">
            <div class="five wide column">
            </div>
            <div class="six wide column" id="login">
              <form class="ui form">
                <div class="field">
                  <input type="text" v-model="loginName" placeholder="学号">
                </div>
                <div class="field">
                  <input type="password" v-model="loginPassword" placeholder="密码">
                </div>
                <br>
              </form>
              <div class="ui buttons" style="width:80%">
                <a class="ui teal button" @click="login">登录</a>
                <div class="or"></div>
                <a class="ui teal button" @click="registerShow = true" >注册</a>
              </div>
            </div>
            <div class="five wide column">
            </div>
          </div>
        </div>
      </div>
    </div>
    <Register v-show="registerShow" v-on:cancel="registerShow = false" v-on:successMethod="registerSuccessShow = true" v-on:failMethod="registerFailShow = true"> </Register>
    <ErrorInfo v-show="loginErrorShow" v-on:cancel="loginErrorShow = false" :msg="msg" :confirm="false"></ErrorInfo>
    <ErrorInfo v-show="registerSuccessShow" v-on:cancel="registerSuccessShow = false" :msg="registerSuccessMsg" :confirm="false"></ErrorInfo>
    <ErrorInfo v-show="registerFailShow" v-on:cancel="registerFailShow = false" :msg="registerFailMsg" :confirm="false"></ErrorInfo>
  </section>
</template>

<script>
import Register from '@/components/Register'
import ErrorInfo from '@/components/ErrorInfo'
import RetreatGroup from '@/components/RetreatGroup'

export default {
  name: 'LoginAndRegister',
  data: function () {
    return {
      loginName: '',
      loginPassword: '',
      registerShow: false,
      loginErrorShow: false,
      registerSuccessShow: false,
      registerFailShow: false,
      msg: '',
      registerSuccessMsg: '注册成功，请登录中南大学邮箱进行验证！',
      registerFailMsg: '注册失败，账号已存在！'
    }
  },
  methods: {
    login: function () {
      if (this.idOrPasswordIsEmpty()) {
        var url = '/api/user/login'
        this.axios.get(url, {
          params: {
            username: this.loginName,
            userpassword: this.loginPassword
          }
        })
          .then((response) => {
            var data = response.data
            console.log(data)
            if (data.status === 1) {
              console.log(data)
              sessionStorage.setItem('user', JSON.stringify(data.user))
              this.$store.dispatch('setUser', data.user)
              this.$router.push({path: '/Main'})
            } else if (data.status === 2) {
              this.msg = '请登录中南邮箱官网进行验证'
              this.loginErrorShow = true
            } else {
              this.msg = '账号或密码错误'
              this.loginErrorShow = true
            }
          })
          .catch(function (error) {
            console.log(error)
          })
      }
    },
    idOrPasswordIsEmpty: function () {
      if (this.loginName === '') {
        this.msg = '学号不能为空'
        this.loginErrorShow = true
      } else if (this.loginPassword === '') {
        this.msg = '密码不能为空'
        this.loginErrorShow = true
      } else {
        return true
      }
    }
  },
  components: {
    Register,
    ErrorInfo,
    RetreatGroup
  },
  beforeRouteEnter: (to, from, next) => {
    next(vm => {
      vm.$store.dispatch('setUser', null)
    })
  }
}
</script>

<style scoped>

</style>
