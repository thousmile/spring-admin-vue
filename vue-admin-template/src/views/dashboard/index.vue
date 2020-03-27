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

    <h1 class="notice">
      因之前有傻逼删除数据，故此关闭一切修改和删除相关的权限！<br>
      想演示全部功能，自己下载代码安装。<br>
      还有删数据的孙子，你全家出门被车撞死！
    </h1>

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

  .notice{
    font-size: 3rem;
    color: red;
    font-weight: 900;
  }
</style>
