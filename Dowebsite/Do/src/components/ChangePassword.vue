<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">修改密码</div>
      <div class="content">
        <form class="ui form">
          <div class="field" style="width: 80%;margin-left:10%; margin-right: 10%; ">
            <input type="password" v-model="password" placeholder="新密码" :style="passwordRedstyle">
          </div>
          <div class="field" style="width: 80%;margin-left:10%; margin-right: 10%; ">
            <input type="password" v-model="passwordRe" placeholder="再输入一次" :style="passwordRepRedstyle" @blur="repeatIsRight">
          </div>
        </form>
      </div>
      <div class="btns">
        <div class="register-btn ui positive button" style="width:80%;margin-left:10%; margin-right: 10%;" @click="changePassword">
          修改密码
        </div>
      </div>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
  </div>
</template>

<script>
import Qs from 'qs'
import ErrorInfo from '@/components/ErrorInfo'
export default {
  name: 'ChangePassword',
  data: function () {
    return {
      password: '',
      passwordRe: '',
      passwordRedstyle: '',
      passwordRepRedstyle: ''
    }
  },
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
    repeatIsRight: function () {
      if (this.password !== this.passwordRe) {
        this.passwordRedstyle = 'border: red solid 0.5px'
        this.passwordRepRedstyle = 'border: red solid 0.5px'
      } else {
        this.passwordRedstyle = ''
        this.passwordRepRedstyle = ''
        return true
      }
    },
    changePassword: function () {
      if (this.paramentsIsEmpty() && this.repeatIsRight()) {
        var url = '/api/user/changepsw'
        var data = Qs.stringify({'StudenId': JSON.parse(sessionStorage.getItem('user')).studentid, 'psw': this.password})
        this.axios.post(url, data)
          .then((result) => {
            var resData = result.data
            console.log(resData)
            if (resData.status === 1) {
              this.closeBtn()
              this.successMethod()
            } else {
              this.failMethod()
            }
          })
          .catch((error) => {
            console.log(error)
            this.failMethod()
          })
      }
    },
    paramentsIsEmpty: function () {
      if (this.password === '') {
        this.passwordRedstyle = 'border: red solid 0.5px'
      } else if (this.passwordRe === '') {
        this.passwordRedstyle = ''
        this.passwordRepRedstyle = 'border: red solid 0.5px'
      } else {
        this.passwordRedstyle = ''
        this.passwordRepRedstyle = ''
        return true
      }
    }
  },
  components: {
    ErrorInfo
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
      height: 240px;
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
