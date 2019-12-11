<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">修改头像</div>
      <div class="content">
        <form class="ui form">
          <div class="field" style="width:40%;height:50%;margin-left:25%;">
            <img  class="ui image" :src=img  style="width:180px;height:180px;">
          </div>
          <br>
          <div class="field" style="width:80%;margin-left:10%; margin-right: 10%;float:left;">
            <input type="text" v-model="fileName" placeholder="文件名" readonly style="width:80%;margin-right: 2%;float:left;">
            <div class="file-box" style="width:18%;;float:left;">
              <input type="file" class="file-btn" @change="choose($event)"/>选择
            </div>
          </div>
        </form>
      </div>
      <div class="btns">
        <div class="register-btn ui positive button" style="width:80%;margin-left:10%; margin-right: 10%;" @click="changeTitle">
          提交头像
        </div>
      </div>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
    <ErrorInfo v-show="uploadShow" v-on:cancel="uploadShow = false" :msg="uploadImgMsg" :confirm="false" :hasMethod="false"></ErrorInfo>
  </div>
</template>

<script>
import Qs from 'qs'
import ErrorInfo from '@/components/ErrorInfo'
export default {
  name: 'ChangPersonalTitle',
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
    changeTitle: function () {
      var url = '/api/user/changeAvatar'
      var data = Qs.stringify({'StudenId': JSON.parse(sessionStorage.getItem('user')).studentid, 'Avatar': this.personalImg})
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
        .catch(function (error) {
          console.log(error)
        })
    },
    choose (event) {
      var file = event.target.files[0]
      var reader = new FileReader()
      let res
      reader.onload = (data) => {
        res = data.target || data.srcElement
      }
      reader.readAsDataURL(file)

      var url = '/api/file/uploaduserimage'
      var zipFormData = new FormData()
      zipFormData.append('file', file)
      zipFormData.append('userid', JSON.parse(sessionStorage.getItem('user')).id)
      let config = { headers: { 'Content-Type': 'multipart/form-data' } }
      this.axios.post(url, zipFormData, config)
        .then((result) => {
          var resData = result.data
          console.log(resData)
          if (resData.status === 1) {
            this.personalImg = resData.url
            this.fileName = file.name
            this.img = res.result
          } else {
            this.uploadShow = true
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  },
  data: function () {
    return {
      submitFile: '',
      fileName: '',
      personalImg: null,
      uploadShow: false,
      uploadImgMsg: '上传头像失败！'
    }
  },
  props: [
    'img'
  ],
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
      height: 400px;
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
    .file-box{
      display: inline-block;
      position: relative;
      text-align: center;
      padding: 3px 5px;
      overflow: hidden;
      color: white;
      background-color: rgb(33,186,69);
      border-radius: 8px;
      height: 35px;
      font-weight: 600;
      &:hover{
        background: rgb(22,171,57);
      }
    }
    .file-btn{
      position: absolute;
      width: 100%;
      height: 100%;
      text-align: center;
      background-color: transparent;
      filter:alpha(opacity=0);
      -moz-opacity:5;
      -khtml-opacity: 0;
      opacity: 0;
      cursor: pointer;
    }
  }
</style>
