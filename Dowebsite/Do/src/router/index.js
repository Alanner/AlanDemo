import Vue from 'vue'
import Router from 'vue-router'
import LoginAndRegister from '@/components/LoginAndRegister'
import Main from '@/components/Main'
import Group from '@/components/Group'
import Task from '@/components/Task'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'LoginAndRegister',
      component: LoginAndRegister
    },
    {
      path: '/Main',
      name: 'Main',
      component: Main
    },
    {
      path: '/Group/:id',
      name: 'Group',
      component: Group
    },
    {
      path: '/Task/:id',
      name: 'Task',
      component: Task
    }
  ]
})
