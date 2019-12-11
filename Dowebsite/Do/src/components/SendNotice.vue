<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">发送通知</div>
      <div class="content">
        <form class="ui form">
          <div class="field" style="width:80%;margin-left:10%; margin-right: 10%; ">
            <input type="text" v-model="noticeTitle" placeholder="公告主题">
          </div>
          <div class="field" style="width: 80%;margin-left:10%; margin-right: 10%; ">
            <input type="text" v-model="noticeText" placeholder="公告信息">
          </div>
          <div class="field" style="width:80%;margin-left:10%; margin-right: 10%; ">
            <input type="text" v-model="noticeDetials" placeholder="公告详细">
          </div>
        </form>
      </div>
      <div class="btns">
        <div class="register-btn ui positive button" style="width:80%;margin-left:10%; margin-right: 10%;" @click="sendNotice">
          发送通知
        </div>
      </div>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
  </div>
</template>

<script>
import Qs from 'qs'
export default {
  name: 'SendNotice',
  data: function () {
    return {
      noticeTitle: '',
      noticeText: '',
      noticeDetials: ''
    }
  },
  props: ['groupId'],
  methods: {
    closeBtn () {
      this.$emit('cancel')
    },
    successMethod: function () {
      this.$emit('successMethod')
    },
    failMethod: function () {
      this.$emit('failMethod')
    },
    sendNotice: function () {
      var url = '/api/Message/upload'
      var data = Qs.stringify({'groupId': this.groupId, 'messagename': this.noticeTitle, 'messagecontent': this.noticeDetials, 'messageabstract': this.noticeText, 'messagepublisher': JSON.parse(sessionStorage.getItem('user')).studentid})
      this.axios.post(url, data)
        .then((result) => {
          var resData = result.data
          console.log(resData)
          if (resData.status === 1) {
            this.closeBtn()
            this.successMethod()
            this.noticeTitle = ''
            this.noticeText = ''
            this.noticeDetials = ''
          } else {
            this.failMethod()
          }
        })
        .catch(function (error) {
          this.failMethod()
          console.log(error)
        })
    }
  }
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
