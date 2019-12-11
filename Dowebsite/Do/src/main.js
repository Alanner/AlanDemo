// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Vuex from 'vuex'
import store from './vuex/store'
import VueParticles from 'vue-particles'

import '../static/css/style.css'
import '../static/lib/font-awesome/css/font-awesome.min.css'
import '../static/lib/semantic/semantic.min.css'
import '../static/lib/bootstrap/css/bootstrap.css'
import '../static/lib/bootstrap/css/bootstrap.min.css'
import '../static/lib/jquery/jquery.min.js'
import '../static/lib/jquery/jquery-migrate.min.js'
import '../static/lib/bootstrap/js/bootstrap.bundle.min.js'
import '../static/lib/bootstrap/js/bootstrap.min.js'
import '../static/lib/easing/easing.min.js'
import '../static/lib/wow/wow.min.js'
import '../static/lib/superfish/hoverIntent.js'
import '../static/lib/superfish/superfish.min.js'
import '../static/lib/magnific-popup/magnific-popup.min.js'
import '../static/lib/semantic/semantic.min.js'
import '../static/js/main.js'

Vue.config.productionTip = false
Vue.prototype.axios = axios
Vue.use(Vuex)
Vue.use(VueParticles)

/* eslint-disable no-new */

new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
