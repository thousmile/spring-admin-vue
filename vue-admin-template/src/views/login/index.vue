<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          v-model="loginForm.username"
          class="form-input"
          name="username"
          type="text"
          auto-complete="on"
          placeholder="请输入用户名"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          v-model="loginForm.password"
          :type="pwdType"
          class="form-input"
          name="password"
          auto-complete="on"
          placeholder="请输入密码"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="pwdType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="16">
          <el-form-item prop="codeText">
            <span class="svg-container">
              <svg-icon icon-class="user"/>
            </span>
            <el-input
              v-model="loginForm.codeText"
              class="code-text"
              name="codeText"
              type="text"
              maxlength="100"
              auto-complete="on"
              placeholder="请输入验证码"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-tooltip
            content="[ 点击 ] 刷新验证码"
            placement="right"
            effect="light"
          >
            <el-image
              :src="codeUrl"
              style="cursor: pointer; border-radius: 5px"
              fit="fit"
              @click="changeImageCode"
            >
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"/>
              </div>
            </el-image>
          </el-tooltip>
        </el-col>
      </el-row>

      <el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          style="width: 100%"
          @click.native.prevent="handleLogin"
        >登录
        </el-button>
      </el-form-item>

      <div class="tips">
        <span
          style="margin-right: 20px"
        >用户名:&nbsp;&nbsp;&nbsp; {{ loginForm.username }}</span><br><br>
        <span>密码: &nbsp;&nbsp;&nbsp; {{ loginForm.password }}</span>
      </div>
    </el-form>

    <copyright/>
  </div>
</template>

<script>
import Copyright from '@/components/Copyright'
import { getUrlPrefix } from '@/utils'
export default {
  name: 'Login',
  components: {
    Copyright
  },
  data() {
    const validateUsername = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('用户名不能小于5位'))
      } else {
        callback()
      }
    }
    const validatePass = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('密码不能小于5位'))
      } else {
        callback()
      }
    }
    const validateCodeText = (rule, value, callback) => {
      if (value.length !== 4) {
        callback(new Error('验证码只能是4位'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: 'admin',
        password: 'admin',
        codeKey: '',
        codeText: ''
      },
      codeUrl: '',
      loginRules: {
        username: [
          { required: true, trigger: 'blur', validator: validateUsername }
        ],
        password: [
          { required: true, trigger: 'blur', validator: validatePass }
        ],
        codeText: [
          { required: true, trigger: 'blur', validator: validateCodeText }
        ]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined,
      otherQuery: {}
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  created() {
    this.randomCodeKey()
    this.changeImageCode()
  },
  mounted() {
    // this.open()
  },
  methods: {
    open() {
      this.$confirm('请不要随意删除数据，否则就扑街！！！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '请继续....!'
          })
        })
        .catch(() => {
          this.$message({
            type: 'error',
            message: '扑街，我顶你个肺！！！'
          })
          window.location.href = 'https://www.baidu.com/'
        })
    },
    // 显示密码
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    // 登录
    handleLogin() {
      const _this = this
      _this.$refs.loginForm.validate((valid) => {
        if (valid) {
          _this.loading = true
          _this.$store.dispatch('Login', this.loginForm)
            .then(() => {
              _this.loading = false
              this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
            })
            .catch((msg) => {
              _this.loading = false
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    },
    changeImageCode() {
      const random = Math.ceil(Math.random() * 100)
      this.codeUrl = `${getUrlPrefix()}/auth/captcha/codes/${this.loginForm.codeKey}?r=${random}`
    },
    // 随机 生成 18位 字符串
    randomCodeKey() {
      let s = []
      const hexDigits = '0123456789abcdefghijklmnopqrstuvwxyz'
      for (let i = 0; i < 24; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1)
      }
      s[14] = '4' // bits 12-15 of the time_hi_and_version field to 0010
      s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1) // bits 6-7 of the clock_seq_hi_and_reserved to 01
      const uuid = s.join('')
      this.loginForm.codeKey = uuid
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$light_gray: #eee;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;

      &:-webkit-autofill {
        -webkit-text-fill-color: #fff !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;

  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }

  .tips {
    font-size: 18px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title {
    font-size: 26px;
    font-weight: 400;
    color: $light_gray;
    margin: 0px auto 40px auto;
    text-align: center;
    font-weight: bold;
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
