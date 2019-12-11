<template>
  <div>
    <div class="content">
      <form class="ui form">
        <br>
        <div class="field" style="width:80%;margin-left:10%; margin-right: 10%; ">
          <input type="text" v-model="start" placeholder="学号区间起始">
        </div>
        <div class="field" style="width: 80%;margin-left:10%; margin-right: 10%; ">
          <input type="text" v-model="total" placeholder="人数">
        </div>
      </form>
    </div>
    <br><br>
    <div class="btns">
      <div class="register-btn ui positive button" style="width:30%;margin-left:18%; margin-right: 5%;" @click="search">
        查询
      </div>
      <div class="register-btn ui positive button" style="width:30%;margin-right: 10%;" @click="invite">
        邀请所有人
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'InviteInfo',
  data: function () {
    return {
      start: '',
      total: ''
    }
  },
  methods: {
    search: function () {
      var url = '/api/user/getlistusers'
      this.axios.get(url, {
        params: {
          StudentId: this.start,
          number: this.total
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.$emit('userlist', data.users)
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    invite: function () {
      //  this.$emit('change')
      var url = '/api/Notification/invitemanytogroup'
      this.axios.get(url, {
        params: {
          groupid: this.start,
          studentid: this.start,
          inviterstudentid: JSON.parse(sessionStorage.getItem('user')).studentid,
          notificationcontent: '邀请加入群组',
          number: this.total
        }
      })
        .then((response) => {
          var data = response.data
          console.log(data)
          if (data.status === 1) {
            console.log(data)
            this.$emit('userlist', data.userlist)
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
