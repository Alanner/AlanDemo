<template>
    <div class="content" style="overflow:auto">
      <div class="ui clearing segment" id="MemberInfo" v-for="(mate,index) of data" :key="index">
        <ResultInvite :mate="mate" :groupid="groupid" :userstudentid="userstudentid"></ResultInvite>
      </div>
    </div>
</template>

<script>
import ResultInvite from '@/components/ResultInvite'
export default {
  name: 'SearchResult',
  props: [
    'data',
    'groupid',
    'userstudentid'
  ],
  methods: {
    closeBtn () {
      this.$emit('cancel')
    }
  },
  components: {
    ResultInvite
  }
}
</script>

<style lang="less" scoped>
      .content{
        color: #797979;
        height: 220px;
        width: 480px;
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
</style>
