<template>
  <div class="copyright">
    托管于<a
      href="https://www.aliyun.com"
      target="_blank"
      title=""
      data-toggle="tooltip"
      data-placement="bottom"
      rel="external nofollow"
      data-original-title="阿里云-为了无法计算的价值"
    >阿里云</a>
    &amp;
    <a
      href="https://www.qiniu.com"
      target="_blank"
      title=""
      data-toggle="tooltip"
      data-placement="bottom"
      rel="external nofollow"
      data-original-title="七牛云-国内领先的企业级云服务商"
    >七牛云</a>
    ·
    <a
      href="https://beian.miit.gov.cn"
      target="_blank"
      title=""
      data-toggle="tooltip"
      data-placement="bottom"
      rel="external nofollow"
      data-original-title="查看备案信息"
    >鄂ICP备2021012008号-1</a>
  </div>
</template>

<script>
export default {
  name: 'Copyright',
  components: {},
  data() {
    return {}
  },
  created() {},
  mounted() {},
  methods: {}
}
</script>

<style lang='scss' scoped>
.copyright {
  color: #eee;
  text-align: center;
  position: fixed;
  left: 0px;
  bottom: 0px;
  width: 100%;
  height: 30px;
  z-index: 9999;
}
</style>
