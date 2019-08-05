<template>
  <div>
    <el-upload :show-file-list="false" :headers="myHeaders" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" :action="getAvatarAction" class="avatar-uploader" accept=".jpg,.jpeg,.png,.gif,.bmp,.JPG,.JPEG,.PBG,.GIF,.BMP">
      <img v-if="imageUrl != ''" :src="imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
  </div>
</template>

<script>

import { mapGetters } from 'vuex'

import { getToken } from '@/utils/auth'

export default {
  name: 'UserAvatar',
  props: {
    avatar: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      imageUrl: '',
      myHeaders: { Authorization: 'Bearer ' + getToken() }
    }
  },
  computed: {
    ...mapGetters([
      'website'
    ]),
    getAvatarAction() {
      // 设置文件上传URL
      return process.env.BASE_API + this.website.upload.avatar
    }
  },
  created() {
    this.imageUrl = this.avatar
  },
  methods: {
    handleAvatarSuccess(res, file) {
      if (res.status === 200) {
        this.imageUrl = res.data.url
        this.$emit('getAvatar', this.imageUrl)
      } else {
        this.$message.error(res.message)
      }
    },
    beforeAvatarUpload(file) {
      const suffix = file.name.substring(file.name.lastIndexOf('.') + 1)
      // 判断图片的格式是否正确
      const isJPEG = suffix === 'jpeg'
      const isPNG = suffix === 'png'
      const isBMP = suffix === 'bmp'
      const isJPG = suffix === 'jpg'
      const isGIF = suffix === 'gif'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPEG && !isPNG && !isBMP && !isJPG && !isGIF) {
        this.$message.error('头像只能是[ jpeg , png , bmp , jpg , gif ]后缀的图片格式!')
      }
      if (!isLt2M) {
        this.$message.error('头像图片大小不能超过2MB!')
      }
      return isJPEG || isPNG || isBMP || isJPG || isGIF && isLt2M
    }
  }
}
</script>

<style lang='scss' scoped>
$size: 100px;

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: $size;
  height: $size;
  line-height: $size;
  text-align: center;
}
.avatar {
  width: $size;
  height: $size;
  display: block;
}
</style>
