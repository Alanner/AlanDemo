<template>
  <div>
    <img  class="ui mini left floated image" :src="mate.avatar" style="margin: 10px">
    <button class="ui mini green right floated button" style="margin: 10px" @click="invite(mate.studentid)" v-show="show">邀请</button>
    <a href="#">{{mate.username}}</a><br>
    <a >{{mate.studentid}}</a>
  </div>
</template>

<script>
export default {
  name: 'ResultInvite',
  props: [
    'mate',
    'groupid',
    'userstudentid'
  ],
  data: function () {
    return {
      show: true
    }
  },
  methods: {
    invite: function () {
      var url = '/api/Notification/invitegroup'
      this.axios.get(url, {
        params: {
          groupid: this.groupid,
          studentid: this.mate.studentid,
          inviterstudentid: this.userstudentid,
          notificationcontent: '快点加入群组'
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.show = false
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>

</style>
