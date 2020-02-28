<template>
  <div class="dashboard-container">
    <div class="dashboard-text">username: {{ username }}</div>
    <div class="dashboard-text">nickname: {{ nickname }}</div>
    <div class="dashboard-text">
      roles:
      <span v-for="role in roles" :key="role">
        <br>
        {{ role }}
      </span>
      <br>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['nickname', 'username', 'roles'])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
