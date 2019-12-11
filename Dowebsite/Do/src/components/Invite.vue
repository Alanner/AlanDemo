<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">{{currentTab.name}}</div>
      <component v-bind:is="currentTab.component" v-on:userlist="showuserlist" :groupid="groupid" :data="users" :userstudentid="userstudentid"></component>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
  </div>
</template>

<script>
import SearchResult from '@/components/SearchResult'
import InviteInfo from '@/components/InviteInfo'

var tabs = [
  {
    name: '邀请加入',
    component: InviteInfo
  },
  {
    name: '搜索结果',
    component: SearchResult
  }
]

export default {
  name: 'Invite',
  methods: {
    closeBtn () {
      this.currentTab = tabs[0]
      this.$emit('cancel')
    },
    showuserlist: function (data) {
      this.users = data
      this.currentTab = tabs[1]
    }
  },
  components: {
    SearchResult
  },
  data: function () {
    return {
      searchResult: false,
      tabs: tabs,
      currentTab: tabs[0],
      users: null
    }
  },
  props: [
    'groupid',
    'userstudentid'
  ]
}
</script>

<style lang="less" scoped>
  .dialog{
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.6);
    z-index: 9999;
    .dialog-container{
      width: 500px;
      height: 300px;
      background: #ffffff;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      border-radius: 8px;
      position: relative;
      .dialog-title{
        width: 100%;
        height: 60px;
        font-size: 18px;
        color: #696969;
        font-weight: 600;
        padding: 16px 50px 0 20px;
        box-sizing: border-box;
      }
      .content{
        color: #797979;
        line-height: 26px;
        padding: 0 20px;
        box-sizing: border-box;
      }
      .btns{
        width: 100%;
        height: 60px;
        position: absolute;
        bottom: 0;
        left: 0;
        text-align: center;
        padding: 0 16px;
        box-sizing: border-box;
        & > div{
          display: inline-block;
          height: 40px;
          line-height: 40px;
          padding: 0 14px;
          color: #ffffff;
          background: #f1f1f1;
          border-radius: 8px;
          margin-right: 12px;
          cursor: pointer;
        }
        .register-btn{
          color: #ffffff;
          background: rgb(33,186,69);
          &:hover{
            background: rgb(22,171,57);
          }
        }
      }
      .close-btn{
        position: absolute;
        top: 16px;
        right: 16px;
        width: 30px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        font-size: 18px;
        cursor: pointer;
        &:hover{
          font-weight: 600;
        }
      }
    }
  }
</style>
