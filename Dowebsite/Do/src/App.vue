<template>
  <body>
    <vue-particles
      color="#7CCD7C"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="4"
      linesColor="#dedede"
      :linesWidth="2"
      :lineLinked="true"
      :lineOpacity="0.5"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
      style="position: absolute;z-index: 1;width: 100%"
    >
    </vue-particles>
    <header id="header">
      <div class="container" >
        <div id="logo" class="pull-left">
          <h1><a href="#intro" class="scrollto">Do</a></h1>
        </div>
        <nav id="nav-menu-container">
          <ul class="nav-menu">
            <li class="menu-active"><a href="#intro"></a></li>
            <li><a href="#" @click="personalCenter">首页</a></li>
            <li><a href="#">使用指南</a></li>
            <li><a href="#">FAQ</a></li>
            <li><a href="#">关于</a></li>
            <li v-show="!isLogin"><a href="" @click="toLogin">登录</a>
            </li>
            <li v-show="isLogin"><a href="">{{username}}</a>
              <ul>
                <li><a href="#" @click="signOut">退出登录</a></li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
    </header>
      <transition :name="transitionName">
        <router-view></router-view>
      </transition>
    <footer id= 'footer'>
      <div class="container">
        <div class="row">
          <div class="col-lg-6 text-lg-left text-center">
            <div class="copyright">
              &copy; Copyright <strong>SHUXIN</strong>. All Rights Reserved
            </div>
            <div class="credits">
              Designed by Bootstrap and Semantic
            </div>
          </div>
          <div class="col-lg-6">
            <nav class="footer-links text-lg-right text-center pt-2 pt-lg-0">
              <a href="#intro" class="scrollto">首页</a>
              <a href="#about" class="scrollto">使用指南</a>
              <a href="#">FAQ</a>
              <a href="#">关于</a>
            </nav>
          </div>
        </div>
      </div>
    </footer>
    <a href="#header" class="back-to-top"><i class="fa fa-chevron-up"></i></a>
  <ErrorInfo v-show="pleaseLogin" @cancel="pleaseLogin = false" :msg="msg"></ErrorInfo>
  </body>
</template>

<script>
import ErrorInfo from '@/components/ErrorInfo'
export default {
  name: 'App',
  data: function () {
    return {
      username: '',
      pleaseLogin: false,
      msg: '请登录您的账号',
      transitionName: 'slide-left-enter'
    }
  },
  methods: {
    signOut: function () {
      sessionStorage.setItem('user', null)
      this.$store.dispatch('setUser', null)
      this.toLogin()
    },
    toLogin: function () {
      this.$router.push({path: '/'})
    },
    personalCenter: function () {
      if (sessionStorage.getItem('user') === 'undefined' || sessionStorage.getItem('user') === 'null') {
        this.pleaseLogin = true
      } else {
        this.$router.push({path: '/Main'})
      }
    }
  },
  computed: {
    isLogin: function () {
      if (sessionStorage.getItem('user') !== 'null') {
        this.$store.commit('userStatus', sessionStorage.getItem('user'))
      } else {
        this.$store.commit('userStatus', null)
      }
      return this.$store.getters.isLogin
    }
  },
  components: {
    ErrorInfo
  },
  mounted: function () {
    if (sessionStorage.getItem('user') === 'undefined' || sessionStorage.getItem('user') === 'null') {
      this.username = '登录'
    } else {
      var user = JSON.parse(sessionStorage.getItem('user'))
      this.username = user.username
    }
  },
  watch: {
    '$route' (to, from) {
      const arr = ['/', '/Main', '/Group/:id', '/Task']
      if (to.path === from.path) {
        this.transitionName = null
      } else if (to.path.match('/Group/*')) {
        const toIndex = 2
        const fromIndex = arr.indexOf(from.path)
        this.transitionName = toIndex > fromIndex ? 'fade-out' : 'fade-in'
      } else if (from.path.match('/Group/*')) {
        const toIndex = arr.indexOf(to.path)
        const fromIndex = 2
        this.transitionName = toIndex > fromIndex ? 'fade-out' : 'fade-in'
      } else {
        const toIndex = arr.indexOf(to.path)
        const fromIndex = arr.indexOf(from.path)
        this.transitionName = toIndex > fromIndex ? 'fade-out' : 'fade-in'
      }
    }
  }
}
</script>
<style>
  .fade-out-enter-active, .fade-out-leave-active {
    transition: all .4s
  }
  .fade-out-enter, .fade-out-leave-active {
    opacity: 0;
    transform: translateX(-100%);
  }

  .fade-in-enter-active, .fade-in-leave-active {
    transition: all .4s
  }
  .fade-in-enter, .fade-in-leave-active {
    opacity: 0;
    transform: translateX(100%);
  }
</style>
