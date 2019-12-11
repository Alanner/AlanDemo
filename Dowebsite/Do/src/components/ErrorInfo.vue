<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">提示</div>
      <div class="content">
        <div class="field" style="width:80%;margin-left:10%;margin-right: 10%;text-align: center;font-size: 20px;font-weight: 600;">
          {{msg}}
        </div>
      </div>
      <div class="btns" style="float: left;" v-show="confirm">
        <div class="register-btn ui positive button" style="width:30%;margin-left:10%; margin-right: 5%;" @click="correctMethod">
          确定
        </div>
        <div class="register-btn ui positive button" style="width:30%;margin-right: 10%;" @click="closeBtn">
          取消
        </div>
      </div>
      <div class="btns" style="float: left;" v-show="!confirm">
        <div class="register-btn ui positive button" style="width:30%;margin-left:4%; margin-right: 5%;" @click="correctMethod">
          确定
        </div>
      </div>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ErrorInfo',
  props: [
    'msg',
    'confirm',
    'hasMethod'
  ],
  methods: {
    closeBtn () {
      this.$emit('cancel')
    },
    correctMethod: function () {
      if (this.hasMethod) {
        this.$emit('method')
        this.$emit('cancel')
      } else {
        this.$emit('cancel')
      }
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
      width: 450px;
      height: 200px;
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
