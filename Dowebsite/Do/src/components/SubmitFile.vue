<template>
  <div class="dialog">
    <div class="dialog-container">
      <div class="dialog-title">提交文件</div>
      <div class="content">
        <form class="ui form">
          <div class="field" style="width:80%;margin-left:10%; margin-right: 10%;float:left;">
            <input type="text" v-model="fileName" placeholder="文件名" readonly style="width:80%;margin-right: 2%;float:left;">
            <div class="file-box" style="width:18%;;float:left;">
              <input type="file" class="file-btn" @change="chooseFile($event)"/>选择
            </div>
          </div>
        </form>
      </div>
      <div class="btns">
        <div class="register-btn ui positive button" style="width:80%;margin-left:10%; margin-right: 10%;" @click="submit">
          提交文件
        </div>
      </div>
      <div class="close-btn"  @click="closeBtn"><i class="iconfont icon-close">X</i></div>
    </div>
    <ErrorInfo v-show="taskNameRight" v-on:cancel="taskNameRight = false" :msg="taskNameRightMsg" :confirm="false"></ErrorInfo>
    <ErrorInfo v-show="taskSubmitWrong" v-on:cancel="taskSubmitWrong = false" :msg="taskSubmitWrongMsg" :confirm="false"></ErrorInfo>
  </div>
</template>

<script>
import ErrorInfo from '@/components/ErrorInfo'
export default {
  name: 'SubmitFile',
  data: function () {
    return {
      submitFile: '',
      fileName: '',
      file: null,
      event: null,
      taskNameRight: false,
      taskNameRightMsg: '所上传的文件名错误！',
      taskSubmitWrong: false,
      taskSubmitWrongMsg: '上传文件失败！'
    }
  },
  props: [
    'taskid',
    'taskname'
  ],
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
    chooseFile (event) {
      this.file = event.target.files[0]
      if (this.file.name.match(this.taskname)) {
        this.fileName = this.file.name
        this.event = event
      } else {
        this.taskNameRight = true
      }
    },
    submit: function () {
      if (this.fileName !== '') {
        var file = this.event.target.files[0]
        var url = '/api/Task/uploadtask'
        var zipFormData = new FormData()
        zipFormData.append('file', file)
        zipFormData.append('uploader', JSON.parse(sessionStorage.getItem('user')).id)
        zipFormData.append('taskid', this.taskid)
        let config = { headers: { 'Content-Type': 'multipart/form-data' } }
        this.axios.post(url, zipFormData, config)
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
      } else {
        this.taskSubmitWrong = true
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
