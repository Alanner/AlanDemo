import Vue from 'vue'
import Vuex from 'vuex'
import * as getters from './getters.js'
import * as mutations from './mutations.js'
import * as actions from './actions.js'
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    currentUser: null,
    isLogin: false
  },
  getters,
  mutations,
  actions
})

export default store
