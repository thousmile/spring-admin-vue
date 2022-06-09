<template>
  <div>
    <br/>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div class="basic-info">
            <div class="text-center">
              <user-avatar :avatar="entity.avatar" @getAvatar="getAvatar"/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <i class="el-icon-s-help"></i>
                昵称
                <div class="pull-right">{{ entity.nickname }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-user-solid"></i>
                用户名
                <div class="pull-right">{{ entity.username }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-chat-line-round"></i>
                邮箱
                <div class="pull-right">{{ entity.email }}</div>
              </li>
              <li class="list-group-item">
                <i class="el-icon-connection"></i>
                部门
                <div class="pull-right">{{ userinfo.dept.deptName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="pre_role"/>
                角色
                <div class="pull-right">
                  <el-tag
                    type="success"
                    v-for="(role, index) in userinfo.roles"
                    :key="index"
                  >{{ role.description }}
                  </el-tag
                  >
                </div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <div class="basic-info">
            <el-tabs v-model="activeName">
              <el-tab-pane label="基本资料" name="first">
                <el-form
                  ref="entity"
                  :model="entity"
                  :rules="rules"
                  label-width="80px"
                >
                  <el-form-item label="用户名" prop="username">
                    <el-input v-model="entity.username" clearable/>
                  </el-form-item>
                  <el-form-item label="用户昵称" prop="nickname">
                    <el-input v-model="entity.nickname" clearable/>
                  </el-form-item>
                  <el-form-item label="邮箱" prop="email">
                    <el-input v-model="entity.email" clearable/>
                  </el-form-item>
                  <el-form-item label="性别">
                    <el-radio-group v-model="entity.gender">
                      <el-radio :label="0">女</el-radio>
                      <el-radio :label="1">男</el-radio>
                      <el-radio :label="2">未知</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submit">立即修改</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="second">
                <el-form
                  ref="updatePwd"
                  :model="updatePwd"
                  :rules="updatePwdRules"
                  label-width="80px"
                >
                  <el-form-item label="旧密码" prop="oldPwd">
                    <el-input
                      v-model="updatePwd.oldPwd"
                      show-password
                      clearable
                    />
                  </el-form-item>
                  <el-form-item label="新密码" prop="newPwd">
                    <el-input
                      v-model="updatePwd.newPwd"
                      show-password
                      clearable
                    />
                  </el-form-item>
                  <el-form-item label="确认密码" prop="confirmPwd">
                    <el-input
                      v-model="updatePwd.confirmPwd"
                      show-password
                      clearable
                    />
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="updatePwdSubmit"
                    >立即修改
                    </el-button
                    >
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import UserAvatar from '@/components/UserAvatar'
import { updateUser, updatePassword } from '@/api/sysUser'
import {
  validateEmail,
  validatePassword,
  validateUsername,
  validateNickname
} from '@/utils/validate'

export default {
  name: 'UserInfo',
  components: {
    UserAvatar
  },
  data() {
    return {
      // 表单校验
      rules: {
        username: [
          {
            required: true,
            message: '登录用户名不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validateUsername,
            trigger: ['blur', 'change']
          }
        ],
        nickname: [
          {
            required: true,
            message: '用户昵称不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validateNickname,
            trigger: ['blur', 'change']
          }
        ],
        email: [
          {
            required: true,
            message: '邮箱地址不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validateEmail,
            trigger: ['blur', 'change']
          }
        ]
      },
      updatePwdRules: {
        oldPwd: [
          {
            required: true,
            message: '旧密码不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validatePassword,
            trigger: ['blur', 'change']
          }
        ],
        newPwd: [
          {
            required: true,
            message: '新密码不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validatePassword,
            trigger: ['blur', 'change']
          }
        ],
        confirmPwd: [
          {
            required: true,
            message: '确认密码不能为空',
            trigger: ['blur', 'change']
          },
          {
            required: true,
            validator: validatePassword,
            trigger: ['blur', 'change']
          }
        ]
      },
      activeName: 'first',
      entity: {
        userId: '',
        avatar: '', // 头像
        username: '', // 登录账户
        email: '', // 邮箱
        nickname: '', // 昵称
        gender: '' // 性别
      },
      updatePwd: {
        userId: '',
        oldPwd: '',
        newPwd: '',
        confirmPwd: ''
      }
    }
  },
  computed: {
    ...mapGetters(['userinfo'])
  },
  created() {
  },
  mounted() {
    this.entity = {
      userId: this.userinfo.userId,
      avatar: this.userinfo.avatar, // 头像
      username: this.userinfo.username, // 登录账户
      email: this.userinfo.email, // 邮箱
      nickname: this.userinfo.nickname, // 昵称
      gender: this.userinfo.gender // 性别
    }

    console.log('userinfo: ', this.userinfo)
    this.updatePwd.userId = this.userinfo.userId
  },
  methods: {
    submit() {
      const _this = this
      _this
        .$confirm('确定要修改个人信息吗？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
        .then(() => {
          _this.$refs.entity.validate((valid) => {
            if (valid) {
              if (_this.entity.userId !== '') {
                updateUser(_this.entity).then((result) => {
                  if (result) {
                    _this.$notify({
                      title: '成功',
                      message: '修改信息成功!',
                      type: 'success'
                    })
                    _this.$router.go(0)
                  }
                })
              }
            }
          })
        })
        .catch((action) => {
          console.log('取消....')
        })
    },
    updatePwdSubmit() {
      const _this = this
      _this
        .$confirm('确定要修改密码吗？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
        .then(() => {
          if (_this.updatePwd.newPwd !== _this.updatePwd.confirmPwd) {
            _this.$message.error('新密码和确认密码不一致！')
            return
          }
          _this.$refs.updatePwd.validate((valid) => {
            if (valid) {
              if (_this.updatePwd.userId !== '') {
                updatePassword(_this.updatePwd).then((result) => {
                  if (result === 1) {
                    _this.$notify({
                      title: '成功',
                      message: '修改密码成功!',
                      type: 'success'
                    })
                    _this.$store.dispatch('LogOut').then(() => {
                      // 为了重新实例化 vue-router 对象，清空之前的路由列表
                      window.location.reload()
                    })
                    // 跳转到 首页
                    _this.$router.push({ name: 'Dashboard' })
                  }
                })
              }
            }
          })
        })
        .catch((action) => {
          console.log('取消....')
        })
    },
    getAvatar(data) {
      // 获取上传头像的值
      console.log('data :>> ', data)
    }
  }
}
</script>

<style lang='scss' scoped>
.list-group {
  padding-left: 0;
  list-style: none;
}

.list-group-striped {
  border-left: 0;
  border-right: 0;
  border-radius: 0;
  padding-left: 0;
  padding-right: 0;
}

.list-group-item {
  border-bottom: 1px solid #e7eaec;
  border-top: 1px solid #e7eaec;
  margin-bottom: -1px;
  padding: 11px 0;

  .svg-icon {
    margin-right: 10px;
  }

  i {
    margin-right: 10px;
  }

}

.pull-right {
  float: right !important;
}

.text-center {
  text-align: center;
}

.basic-info {
  height: 500px;
}
</style>
