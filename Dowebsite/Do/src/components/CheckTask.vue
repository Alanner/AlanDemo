<template>
  <div style="overflow:auto;height: 580px">
    <div class="ui container" v-for="(task,index) of info" :key="index" v-show="info.length !== 0">
      <TaskInfo :task=task :id="id"></TaskInfo>
      <div class="ui divider"></div>
    </div>
    <div class="ui container" v-show="info.length === 0">
      暂无任务
    </div>
  </div>
</template>

<script>
import TaskInfo from '@/components/TaskInfo'

export default {
  name: 'CheckTask',
  props: [
    'info',
    'id'
  ],
  components: {
    TaskInfo
  }
}
</script>

<style scoped>

</style>
