<template>
  <el-container>
    <el-header>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button v-has="'pre_role:create'" type="primary" @click="addRoleEntity">新增角色</el-button>
        </el-col>
        <el-col :span="20">
          <el-alert title="请勿乱删除角色, 确实需要删除角色的时候, (1).请确定这个角色下面没有用户, (2).请确定这个角色没有分配权限. 只有满足以上两种情况才可以删除角色成功!" type="info" />
        </el-col>
      </el-row>

      <el-dialog :visible.sync="dialog.visible" :before-close="handleClose" :title="dialog.title" width="550px">
        <el-form id="role" ref="entity" :model="entity" :rules="rules" label-width="80px">
          <template v-if="entity.rid > 0">
            <el-form-item label="角色ID">
              <el-input v-model="entity.rid" disabled />
            </el-form-item>
          </template>
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="entity.roleName" clearable />
          </el-form-item>
          <el-form-item label="角色描述" prop="description">
            <el-input v-model="entity.description" clearable />
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog.visible = false">取 消</el-button>
          <el-button type="primary" @click="saveAndFlush">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 角色权限列表 -->
      <el-dialog :visible.sync="authority.visible" :title="authority.title" :before-close="handleClose" width="400px">
        <el-tree
          ref="treeList"
          :data="authority.list"
          :props="authority.props"
          :default-checked-keys="authority.checkedKeys"
          show-checkbox
          highlight-current
          node-key="id"
        />

        <span slot="footer" class="dialog-footer">
          <el-button @click="authority.visible = false">取 消</el-button>
          <el-button type="primary" @click="updateRolePermissions">确 定</el-button>
        </span>
      </el-dialog>
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
        <el-table-column fixed prop="rid" label="角色ID" width="100" />
        <el-table-column prop="roleName" label="角色名称" />
        <el-table-column prop="description" label="角色描述" />
        <el-table-column label="添加时间">
          <template slot-scope="scope">{{ scope.row.createTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="scope">{{ scope.row.lastUpdateTime | formatDateTime }}</template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <el-button v-has="'pre_role:update:permissions'" type="info" @click="getRolePermissions(scope.row)">修改权限</el-button>
            <el-button v-has="'pre_role:update'" type="primary" @click="updateRoleEntity(scope.row)">编辑</el-button>
            <el-button v-has="'pre_role:delete'" type="danger" @click="deleteRoleEntity(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
        :page-size="page.pageSize"
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
  getRolePage,
  getRoleById,
  saveRole,
  updateRole,
  removeRoleById,
  updateRolePermission
} from '@/api/sysRole'

export default {
  name: 'Role',
  components: {},
  data() {
    return {
      page: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        total: 0
      },
      entity: {
        rid: 0,
        description: '',
        roleName: ''
      },
      dialog: {
        visible: false,
        title: ''
      },
      tableData: [],
      loading: false,
      authority: {
        list: [],
        props: {
          label: 'title',
          children: 'children'
        },
        checkedKeys: [],
        rid: 0,
        visible: false,
        title: ''
      },
      options: [
        { value: 0, label: '被删除' },
        { value: 1, label: '正常' },
        { value: 2, label: '隐藏' }
      ],
      // 校验规则
      rules: {
        roleName: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /[R][O][L][E][_][A-Z]{4,}/
              if (!regex.test(value)) {
                callback(
                  new Error('角色名称必须是全大写英文,并且要以“ROLE_”开头')
                )
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        description: [
          { required: true, message: '角色描述不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      // 获取角色列表
      const _this = this
      _this.loading = true
      getRolePage(_this.page).then(result => {
        _this.tableData = result.list
        _this.page.total = result.total
        _this.loading = false
      })
    },
    currentChange(index) {
      // 切换分页
      this.page.pageNum = index
      this.getTableData()
    },
    emptyEntity() {
      this.entity = {
        rid: 0,
        description: '',
        roleName: ''
      }
    },
    addRoleEntity() {
      this.emptyEntity()
      this.dialog.title = '新增角色'
      this.dialog.visible = true
    },
    updateRoleEntity(data) {
      this.emptyEntity()
      this.entity = {
        rid: data.rid,
        roleName: data.roleName,
        description: data.description
      }
      this.dialog.title = '修改角色'
      this.dialog.visible = true
    },
    deleteRoleEntity(data) {
      // 删除角色，如果当前角色有跟其他用户或者权限关联，无法删除
      const _this = this
      if (data.rid > 0) {
        _this
          .$confirm(
            '确定要删除【' +
              data.description +
              '】吗? 请确认这个角色下面没有用户了.否则无法删除 是否继续?',
            '警告',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }
          )
          .then(() => {
            removeRoleById(data.rid).then(result => {
              _this.$notify({
                title: '成功',
                message: '删除成功!',
                type: 'success'
              })
              _this.getTableData()
            })
          })
      } else {
        _this.$notify.error({
          title: '错误',
          message: '请先选中角色,才可以删除'
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
          if (_this.entity.rid > 0) {
            // 修改角色信息
            updateRole(_this.entity).then(result => {
              _this.$notify({
                title: '成功',
                message: '修改角色成功!',
                type: 'success'
              })
              _this.getTableData()
              _this.dialog.visible = false
            })
          } else {
            // 新增角色
            saveRole(_this.entity).then(result => {
              _this.$notify({
                title: '成功',
                message: '新增角色成功!',
                type: 'success'
              })
              _this.getTableData()
              _this.dialog.visible = false
            })
          }
        }
      })
    },
    getRolePermissions(data) {
      // 查看当前角色拥有的权限
      const _this = this
      if (data.rid > 0) {
        getRoleById(data.rid).then(result => {
          _this.authority.list = result.all
          _this.authority.checkedKeys = result.have
          _this.authority.rid = data.rid
          _this.authority.title = data.description
          _this.authority.visible = true
        })
      }
    },
    updateRolePermissions() {
      // 给角色修改权限
      const _this = this
      const list = _this.$refs.treeList.getCheckedKeys()
      const father = _this.$refs.treeList.getHalfCheckedNodes()
      if (father !== undefined && father !== null && father.length > 0) {
        father.forEach(f => list.push(f.id))
      }
      updateRolePermission({
        rid: _this.authority.rid,
        permissions: list
      }).then(result => {
        _this.$notify({
          title: '成功',
          message: '修改角色权限成功!',
          type: 'success'
        })
        _this.getTableData()
        _this.authority.visible = false
      })
    }
  }
}
</script>
<style lang='scss' scoped>
</style>

<style>
#role .el-alert__title {
  font-size: 1rem;
}
</style>

