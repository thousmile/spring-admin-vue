<template>
  <el-container id="user">
    <el-header>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            v-model="page.keywords"
            clearable
            placeholder="请输入关键字"
          />
        </el-col>
        <el-col :span="6">
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getUserTableData"
          >搜索</el-button>
        </el-col>
        <el-col :span="4">
          <el-button
            v-has="'pre_user:create'"
            type="info"
            @click="addUserEntity"
          >添加用户</el-button>
        </el-col>
        <el-col :span="6">
          <!-- 修改用户信息 -->
          <el-dialog
            :visible.sync="dialog.visible"
            :title="dialog.title"
            width="30%"
            :before-close="handleClose"
          >
            <el-form
              id="user"
              ref="entity"
              :model="entity"
              :rules="rules"
              label-width="80px"
            >
              <template v-if="entity.uid != ''">
                <el-form-item label="用户ID">
                  <el-input v-model="entity.uid" disabled />
                </el-form-item>
              </template>
              <el-form-item label="头像" prop="avatar">
                <user-avatar :avatar="entity.avatar" @getAvatar="getAvatar" />
              </el-form-item>
              <el-form-item label="昵称" prop="nickname">
                <el-input v-model="entity.nickname" clearable />
              </el-form-item>
              <el-form-item label="账号" prop="username">
                <el-input v-model="entity.username" clearable />
              </el-form-item>
              <template v-if="entity.uid == ''">
                <el-form-item label="密码" prop="password">
                  <el-input
                    v-model="entity.password"
                    type="password"
                    clearable
                  />
                </el-form-item>
              </template>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="entity.email" clearable />
              </el-form-item>
              <el-form-item label="生日" prop="birthday">
                <el-date-picker
                  v-model="entity.birthday"
                  value-format="yyyy-MM-dd"
                  type="date"
                  :picker-options="pickerOptions"
                  placeholder="选择日期"
                />
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-select
                  v-model="entity.gender"
                  clearable
                  filterable
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in genderOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="部门" prop="deptId">
                <el-cascader
                  v-model="entity.deptId"
                  :show-all-levels="false"
                  :options="deptTree"
                  clearable
                  filterable
                  :props="cascaderProps"
                />
              </el-form-item>
              <el-form-item label="状态" prop="status">
                <el-select
                  v-model="entity.status"
                  clearable
                  filterable
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in optionsStatus"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialog.visible = false">取 消</el-button>
              <el-button type="primary" @click="saveAndFlush">确 定</el-button>
            </span>
          </el-dialog>

          <!-- 修改用户角色 -->
          <el-dialog
            :visible.sync="userRole.visible"
            :title="userRole.title"
            width="30%"
            :before-close="handleClose"
          >
            <el-checkbox-group v-model="entity.roles">
              <el-checkbox
                v-for="(item, index) in roleList"
                :key="index"
                :label="item.rid"
              >{{ item.description }}</el-checkbox>
            </el-checkbox-group>
            <span slot="footer" class="dialog-footer">
              <el-button @click="userRole.visible = false">取 消</el-button>
              <el-button type="primary" @click="saveUserRoles">确 定</el-button>
            </span>
          </el-dialog>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中..."
        element-loading-spinner="el-icon-loading"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column prop="nickname" label="用户昵称" fixed />
        <el-table-column prop="uid" label="用户ID" width="80" />
        <el-table-column label="头像" width="100">
          <template slot-scope="scope">
            <div
              class="avatar-wrapper"
              @click="viewBigAvatar(scope.row.avatar)"
            >
              <img :src="scope.row.avatar" class="user-avatar">
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" />
        <el-table-column label="性别" width="50">
          <template slot-scope="scope">{{
            scope.row.gender | showGender
          }}</template>
        </el-table-column>
        <el-table-column label="生日" width="100">
          <template slot-scope="scope">{{
            scope.row.birthday | formatDate
          }}</template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="拥有角色">
          <template slot-scope="scope">
            <el-tooltip
              v-for="(role, index) in scope.row.roles"
              :key="index"
              :content="role.description"
              class="item"
              effect="dark"
              placement="top-start"
            >
              <el-tag>{{ role.roleName }}</el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="所在部门" width="100">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.departmentName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="70">
          <template slot-scope="scope">{{
            scope.row.status | showStatus
          }}</template>
        </el-table-column>
        <el-table-column label="添加时间">
          <template slot-scope="scope">{{
            scope.row.createTime | formatDateTime
          }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="scope">{{
            scope.row.lastUpdateTime | formatDateTime
          }}</template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <el-button
              v-has="'pre_user:update:roles'"
              type="info"
              @click="updateUserRole(scope.row)"
            >修改角色</el-button>
            <el-button
              v-has="'pre_user:update'"
              type="primary"
              @click="updateUserEntity(scope.row)"
            >编辑</el-button>
            <el-button
              v-has="'pre_user:delete'"
              type="danger"
              @click="deleteUserEntity(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
        :page-size="page.pageNum"
        :total="page.total"
        layout="total,prev, pager, next, jumper"
        background
        @current-change="currentChange"
      />
    </el-footer>
  </el-container>
</template>

<script>
import {
  getUserPage,
  saveUser,
  updateUser,
  removeUserById,
  updateUserRoles
} from '@/api/sysUser'
import { getDeptTree } from '@/api/sysDept'
import { getRoleAll } from '@/api/sysRole'
import {
  validateEmail,
  validatePassword,
  validateUsername,
  validateNickname
} from '@/utils/validate'

import UserAvatar from '@/components/UserAvatar'

export default {
  name: 'User',
  components: {
    UserAvatar
  },
  data() {
    return {
      page: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        total: 0
      },
      entity: {
        uid: '',
        avatar: '', // 头像
        username: '', // 登录账户
        email: '', // 邮箱
        nickname: '', // 昵称
        password: '', // 密码
        gender: '', // 性别
        birthday: '', // 生日
        status: 0, // 状态
        deptId: 0, // 部门ID
        roles: [] // 角色列表
      },
      dialog: {
        visible: false,
        title: ''
      },
      userRole: {
        visible: false,
        title: ''
      },
      loading: false,
      tableData: [],
      deptTree: [],
      roleList: [],
      cascaderProps: {
        children: 'children',
        label: 'title',
        value: 'id',
        emitPath: false,
        checkStrictly: true
      },
      optionsStatus: [
        { value: 0, label: '禁用' },
        { value: 1, label: '正常' },
        { value: 2, label: '隐藏' }
      ],
      genderOptions: [
        { value: 0, label: '女' },
        { value: 1, label: '男' },
        { value: 2, label: '未知' }
      ],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        }
      },
      // 校验规则
      rules: {
        avatar: [{ required: true, message: '头像不能为空', trigger: 'blur' }],
        username: [
          { required: true, message: '登录账号不能为空', trigger: 'blur' },
          { required: true, validator: validateUsername, trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '昵称不能为空', trigger: 'blur' },
          { required: true, validator: validateNickname, trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { required: true, validator: validatePassword, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' },
          { required: true, validator: validateEmail, trigger: 'blur' }
        ],
        gender: [{ required: true, message: '性别必须选择~', trigger: 'blur' }],
        birthday: [
          { required: true, message: '生日必须选择~', trigger: 'blur' }
        ],
        status: [{ required: true, message: '状态必须选择~', trigger: 'blur' }],
        deptId: [{ required: true, message: '部门必须选择~', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getUserTableData()
    this.getDeptListData()
  },
  methods: {
    getUserTableData() {
      const _this = this
      _this.loading = true
      getUserPage(_this.page).then(result => {
        _this.tableData = result.list
        _this.page.total = result.total
        _this.loading = false
      })
    },
    getDeptListData() {
      // 获取部门列表
      const _this = this
      _this.loading = true
      getDeptTree().then(result => {
        _this.deptTree = result
        _this.loading = false
      })
    },
    getRoleListData() {
      // 获取角色列表
      const _this = this
      _this.loading = true
      getRoleAll().then(result => {
        _this.roleList = result
        _this.loading = false
      })
    },
    currentChange(pageNum) {
      // 切换分页
      this.page.pageNum = pageNum
      this.getUserTableData()
    },
    viewBigAvatar(url) {
      // 查看大头像
      window.open(url)
    },
    updateUserRole(data) {
      // 重新给用户赋值权限
      this.getRoleListData()
      this.emptyEntity()
      this.userRole = {
        visible: true,
        title: '修改用户角色'
      }
      const roles = []
      data.roles.forEach(role => {
        roles.push(role.rid)
      })
      this.entity.uid = data.uid
      this.entity.nickname = data.nickname
      this.entity.roles = roles
    },
    saveUserRoles() {
      // 保存用户的角色信息
      const _this = this
      const roles = this.entity.roles
      if (roles !== null && roles.length > 0) {
        _this
          .$confirm(
            '确定要修改【' + _this.entity.nickname + '】的角色吗? 是否继续?',
            '警告',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
          )
          .then(() => {
            updateUserRoles({ uid: _this.entity.uid, roles: roles }).then(
              result => {
                _this.$message({ type: 'success', message: '修改角色成功!' })
                _this.userRole.visible = false
                _this.getUserTableData()
              }
            )
          })
      } else {
        _this.$notify.error({
          title: '错误',
          message: '请先选中角色,才可以修改'
        })
      }
    },
    getAvatar(data) {
      // 获取上传头像的值
      this.entity.avatar = data
    },
    emptyEntity() {
      // 清空用户信息
      this.entity = {
        uid: '',
        avatar: '', // 头像
        username: '', // 登录账户
        email: '', // 邮箱
        nickname: '', // 昵称
        password: '', // 密码
        gender: '', // 性别
        birthday: '', // 生日
        status: '', // 状态
        deptId: '', // 部门ID
        roles: [] // 角色列表
      }
    },
    addUserEntity() {
      // 添加用户
      this.getDeptListData()
      this.emptyEntity()
      this.dialog = {
        visible: true,
        title: '新建用户'
      }
    },
    handleNodeClick(data) {
      console.log('data :', data)
    },
    updateUserEntity(data) {
      // 修改用户信息
      this.emptyEntity()
      this.entity = {
        uid: data.uid,
        avatar: data.avatar, // 头像
        username: data.username, // 登录账户
        email: data.email, // 邮箱
        nickname: data.nickname, // 昵称
        password: data.password, // 密码
        gender: data.gender, // 性别
        birthday: data.birthday, // 生日
        status: data.status, // 状态
        deptId: data.deptId, // 部门ID
        roles: data.roles // 角色列表
      }
      this.dialog = {
        visible: true,
        title: '修改用户信息'
      }
    },
    deleteUserEntity(data) {
      // 删除这个用户
      const _this = this
      if (data.uid !== null && data.uid !== '') {
        _this
          .$confirm(
            '确定要删除【' + data.nickname + '】吗? 是否继续?',
            '警告',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
          )
          .then(() => {
            removeUserById(data.uid).then(result => {
              _this.$message({ type: 'success', message: '删除成功!' })
              _this.getUserTableData()
            })
          })
      } else {
        _this.$notify.error({
          title: '错误',
          message: '请先选中用户,才可以删除'
        })
      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.entity.validate(valid => {
        if (valid) {
          delete _this.entity.roles
          if (_this.entity.uid !== '') {
            updateUser(_this.entity).then(result => {
              _this.$notify({
                title: '成功',
                message: '修改用户成功!',
                type: 'success'
              })
              _this.getUserTableData()
              _this.dialog.visible = false
            })
          } else {
            saveUser(_this.entity).then(result => {
              _this.$notify({
                title: '成功',
                message: '添加用户成功!',
                type: 'success'
              })
              _this.getUserTableData()
              _this.dialog.visible = false
            })
          }
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.avatar-wrapper {
  cursor: pointer;
  position: relative;
  .user-avatar {
    width: 50px;
    height: 50px;
    border-radius: 10px;
  }
}
</style>
<style lang="scss">
#user {
  .el-tag:hover {
    cursor: pointer;
  }
  .el-date-editor {
    width: 100%;
  }
  .el-cascader {
    width: 100%;
  }
  .el-select {
    width: 100%;
  }
}
</style>
