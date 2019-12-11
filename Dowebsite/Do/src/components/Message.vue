<template>
  <div style="overflow:auto;height: 580px">
    <div class="ui container" v-for="(message,index) of info" :key="index" v-show="info.length !== 0">
      <MessageInfo :message=message @method="method"></MessageInfo>
      <div class="ui divider"></div>
    </div>
    <div class="ui container" v-show="info.length === 0">
      暂无通知
    </div>
  </div>
</template>

<script>
import MessageInfo from '@/components/MessageInfo'

export default {
  name: 'Message',
  props: [
    'info'
  ],
  components: {
    MessageInfo
  },
  methods: {
    method () {
      this.$emit('method')
    }
  }
}
</script>

<style scoped>

</style>
