<template>
  <el-container v-has="'pre_role:view'">
    <el-header>
      <div v-title>角色管理</div>

      <el-row :gutter="20">
        <el-col :span="4">
          <el-button v-has="'pre_role:new'" type="primary" @click="addRoleEntity">新增角色</el-button>
        </el-col>
        <el-col :span="20">
          <el-alert :title="website.role.alert" type="info"/>
        </el-col>
      </el-row>

      <el-dialog :visible.sync="dialog.visible" :title="dialog.title" width="550px">
        <el-form id="role" ref="entity" :model="entity" :rules="rules" label-width="80px">
          <template v-if="entity.rid > 0">
            <el-form-item label="角色ID">
              <el-input v-model="entity.rid" disabled/>
            </el-form-item>
          </template>
          <el-form-item label="角色名称" prop="name">
            <el-input v-model="entity.name" clearable/>
          </el-form-item>
          <el-form-item label="角色描述" prop="describe">
            <el-input v-model="entity.describe" clearable/>
          </el-form-item>
          <el-form-item label="角色状态" prop="state">
            <el-select v-model="entity.state" placeholder="请选择">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog.visible = false">取 消</el-button>
          <el-button type="primary" @click="saveAndFlush">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 角色权限列表 -->
      <el-dialog :visible.sync="authority.visible" :title="authority.title" width="400px">
        <el-tree
          ref="tree"
          :data="authority.list"
          :props="authority.props"
          :default-checked-keys="authority.checkedKeys"
          show-checkbox
          highlight-current
          node-key="pid"/>
        <span slot="footer" class="dialog-footer">
          <el-button @click="authority.visible = false">取 消</el-button>
          <el-button type="primary" @click="updatePermission">确 定</el-button>
        </span>
      </el-dialog>

    </el-header>
    <el-main>
      <el-table v-loading="loading" :data="tableData" border style="width: 100%">
        <el-table-column fixed prop="rid" label="角色ID" width="100"/>
        <el-table-column prop="name" label="角色名称"/>
        <el-table-column prop="describe" label="角色描述"/>
        <el-table-column label="角色状态">
          <template slot-scope="scope">
            {{ scope.row.state | showState }}
          </template>
        </el-table-column>
        <el-table-column label="添加时间">
          <template slot-scope="scope">
            {{ scope.row.createTime | formatTime }}
          </template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="scope">
            {{ scope.row.upTime | formatTime }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <el-button type="info" size="small" @click="rolePermission(scope.row)">修改权限</el-button>
            <el-button v-has="'pre_role:update'" type="primary" size="small" @click="updateRoleEntity(scope.row)">编辑</el-button>
            <el-button v-has="'pre_role:delete'" type="danger" size="small" @click="deleteRoleEntity(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <el-pagination
        :page-size="page.size"
        :total="page.total"
        layout="prev, pager, next, jumper"
        @current-change="currentChange"/>
    </el-footer>
  </el-container>
</template>

<script>

import { getRolePage, getRoleById, saveRole, updateRole, removeRoleById, updateRolePermission } from '@/api/role'

import { mapGetters } from 'vuex'

export default {
  components: {},
  data() {
    return {
      page: {
        page: 1,
        size: 10,
        search: null,
        total: 0
      },
      entity: {
        rid: 0,
        describe: '',
        name: '',
        state: ''
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
          children: 'children',
          label: 'title'
        },
        checkedKeys: [],
        rid: 0,
        visible: false,
        title: ''
      },
      options: [{ value: 0, label: '被删除' }, { value: 1, label: '正常' }, { value: 2, label: '隐藏' }],
      // 校验规则
      rules: {
        state: [{ required: true, message: '角色状态不能为空', trigger: 'blur' }],
        describe: [{ required: true, message: '角色描述不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '角色名称不能为空', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /[R][O][L][E][_][A-Z]{4,}/
              if (!regex.test(value)) {
                callback(new Error('角色名称必须是全大写英文,并且要以“ROLE_”开头'))
              } else {
                callback()
              }
            }, trigger: 'blur'
          }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'website'
    ])
  },
  created() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      // 获取角色列表
      const _this = this
      _this.loading = true
      getRolePage(_this.page).then((result) => {
        if (result.status === 200) {
          _this.tableData = result.data.content
          _this.page.total = result.data.totalElements
          _this.loading = false
        }
      }).catch((err) => {
        console.log('err :', err)
      })
    },
    currentChange(index) {
      // 切换分页
      this.page.page = index
      this.getTableData()
    },
    emptyEntity() {
      this.entity.rid = 0
      this.entity.describe = ''
      this.entity.name = ''
      this.entity.state = 1
    },
    addRoleEntity() {
      this.emptyEntity()
      this.dialog.title = '新增角色'
      this.dialog.visible = true
    },
    updateRoleEntity(data) {
      this.emptyEntity()
      this.entity.rid = data.rid
      this.entity.describe = data.describe
      this.entity.name = data.name
      this.entity.state = data.state
      this.entity.createTime = data.createTime
      this.dialog.title = '修改角色'
      this.dialog.visible = true
    },
    deleteRoleEntity(data) {
      // 删除角色，如果当前角色有跟其他用户或者权限关联，无法删除
      const _this = this
      if (data.rid > 0) {
        _this.$confirm('确定要删除【' + data.describe + '】吗? 请确认这个角色下面没有用户了.否则无法删除 是否继续?', '警告',
          { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
          .then(() => {
            removeRoleById(data.rid).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '删除成功!', type: 'success' })
                _this.getTableData()
              }
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }).catch(() => {
            _this.$message({ type: 'info', message: '已取消删除' })
          })
      } else {
        _this.$notify.error({ title: '错误', message: '请先选中角色,才可以删除' })
      }
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.entity.validate(valid => {
        if (valid) {
          if (_this.entity.rid > 0) {
            // 修改角色信息
            updateRole(_this.entity).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '修改角色成功!', type: 'success' })
                _this.getTableData()
                _this.dialog.visible = false
              }
            }).catch((err) => {
              console.log('err :', err)
            })
          } else {
            // 新增角色
            saveRole(_this.entity).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '新增角色成功!', type: 'success' })
                _this.getTableData()
                _this.dialog.visible = false
              }
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }
        }
      })
    },
    rolePermission(data) {
      // 查看当前角色拥有的权限
      const _this = this
      if (data.rid > 0) {
        getRoleById(data.rid).then((result) => {
          if (result.status === 200) {
            _this.authority.list = result.data.all
            _this.authority.checkedKeys = result.data.current
            _this.authority.rid = data.rid
            _this.authority.title = data.describe
            _this.authority.visible = true
          }
        }).catch((err) => {
          console.log('err :', err)
          _this.$notify.error({ title: '错误', message: err.message })
        })
      }
    },
    updatePermission() {
      // 给角色修改权限
      const _this = this
      const list = _this.$refs.tree.getCheckedKeys()
      const father = _this.$refs.tree.getHalfCheckedNodes()
      if (father != null && father.length > 0) {
        father.forEach(f => {
          list.push(f.pid)
        })
      }
      console.log('list :', list)
      updateRolePermission({ rid: _this.authority.rid, pids: list }).then((result) => {
        if (result.status === 200) {
          _this.$notify({ title: '成功', message: '修改角色权限成功!', type: 'success' })
          _this.getTableData()
          _this.authority.visible = false
        }
      }).catch((err) => {
        console.log('err :', err)
        _this.$notify.error({ title: '错误', message: err.message })
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

