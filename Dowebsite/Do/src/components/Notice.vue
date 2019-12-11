<template>
  <div style="overflow:auto;height: 580px">
    <div class="ui container" v-for="(notice,index) of info" :key="index" v-show="info.length !== 0">
      <NoticeInfo :notice=notice :id="id" @method="method"></NoticeInfo>
      <div class="ui divider"></div>
    </div>
    <div class="ui container" v-show="info.length === 0">
      暂无公告
    </div>
  </div>
</template>

<script>
import NoticeInfo from '@/components/NoticeInfo'

export default {
  name: 'Notice',
  props: [
    'info',
    'id'
  ],
  components: {
    NoticeInfo
  },
  methods: {
    method: function () {
      this.$emit('method')
    }
  }
}
</script>

<style scoped>

</style>
