<template>
  <div>
    <el-tooltip class="item" effect="dark" :content="nickname" placement="top-start">
      <div
        class="avatar-wrapper"
        @click="viewBigAvatar"
      >
        <img :src="avatar" class="user-avatar" alt="头像">
      </div>
    </el-tooltip>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'ShowUserAvatar',
  props: {
    userId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      nickname: '',
      avatar: ''
    }
  },
  computed: {
    ...mapGetters(['simpleUsers'])
  },
  created() {
    this.getNickname()
    this.getAvatar()
  },
  methods: {
    viewBigAvatar() {
      window.open(this.avatar)
    },
    getNickname() {
      const _this = this
      for (const index in _this.simpleUsers) {
        let value = _this.simpleUsers[index]
        if (value && value.userId === _this.userId) {
          _this.nickname = value.username
          return
        }
      }
    },
    getAvatar() {
      const _this = this
      for (const index in _this.simpleUsers) {
        let value = _this.simpleUsers[index]
        if (value && value.userId === _this.userId) {
          _this.avatar = value.avatar
          return
        }
      }
    }
  }
}
</script>

<style lang='scss' scoped>

</style>
