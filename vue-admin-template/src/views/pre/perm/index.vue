<template>
  <div
    id="pmn"
    v-loading="loading"
    v-has="'pre_perm:view'"
    class="wrapper"
    element-loading-text="拼命加载中..."
    element-loading-spinner="el-icon-loading"
  >
    <el-row :gutter="20">
      <el-col :span="10">
        <el-input v-model="filterText" clearable placeholder="输入关键字进行过滤">
          <i slot="prefix" class="el-input__icon el-icon-search" />
        </el-input>
        <br>
        <br>
        <el-tree ref="tree" :data="treeData" :props="defaultProps" accordion :filter-node-method="filterNode" @node-click="handleNodeClick" />
      </el-col>
      <el-col :span="14">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="card-header">权限详细信息</span>
            <el-button-group style="float: right;">
              <el-button v-has="'pre_perm:create'" type="success" icon="el-icon-share" @click="addEntity">新增
              </el-button>
              <el-button v-has="'pre_perm:update'" type="warning" icon="el-icon-edit" @click=" isEdit = !isEdit ">编辑
              </el-button>
              <el-button v-has="'pre_perm:delete'" type="danger" icon="el-icon-delete" @click="deleteEntity">删除
              </el-button>
            </el-button-group>
          </div>

          <el-form ref="pmnForm" :rules="rules" :model="pmnForm" label-width="100px">
            <el-form-item label="权限ID">
              <el-input v-model="pmnForm.permissionId" :disabled="true" />
            </el-form-item>

            <el-form-item label="权限标题" prop="title">
              <el-input v-model="pmnForm.title" :disabled="isEdit" clearable />
            </el-form-item>

            <template v-if="pmnForm.permType === 0">
              <el-form-item label="权限图标" prop="icon">
                <el-input v-model="pmnForm.icon" :disabled="isEdit" clearable />
              </el-form-item>
            </template>

            <el-form-item label="唯一标识" prop="perms">
              <el-input
                v-model="pmnForm.perms"
                :disabled="isEdit"
                clearable
                placeholder="强调，必须是唯一的英文字母"
              />
            </el-form-item>

            <el-form-item label="上级权限" prop="parentId">
              <el-cascader
                v-model="pmnForm.parentId"
                :disabled="isEdit"
                :show-all-levels="false"
                :options="menuTreeData"
                clearable
                filterable
                :props="cascaderProps"
              >
                <template slot-scope="{ node, data }">
                  <span>{{ data.title }}</span>
                  <span v-if="!node.isLeaf">({{ data.children.length }})</span>
                </template>
              </el-cascader>
            </el-form-item>

            <el-form-item label="权限类型" prop="type">
              <el-select v-model="pmnForm.permType" :disabled="isEdit" placeholder="选择类型">
                <el-option v-for="(item,index) in permTypeOptions" :key="index" :label="item.name" :value="item.value" />
              </el-select>
            </el-form-item>

            <el-form-item label="权限排序" prop="sort">
              <el-input-number v-model="pmnForm.sort" :disabled="isEdit" :min="1" :max="9999" clearable />
            </el-form-item>

            <el-form-item v-if="isEdit === false">
              <el-button type="primary" @click="saveAndFlush">保存</el-button>
              <el-button @click=" isEdit = !isEdit ">取消</el-button>
            </el-form-item>

          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  getPermissionTree,
  savePermission,
  updatePermission,
  removePermissionById
} from '@/api/sysPermission'

export default {
  name: 'Perm',
  data() {
    return {
      pmnForm: {
        permissionId: '',
        title: '',
        parentId: '',
        perms: '',
        icon: '',
        sort: '',
        permType: ''
      },
      filterText: '',
      loading: false,
      isEdit: true, // 是否可以编辑
      treeData: [],
      menuTreeData: [],
      permTypeOptions: [
        { value: 1, name: '按钮' },
        { value: 0, name: '菜单' }
      ],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      cascaderProps: {
        children: 'children',
        label: 'title',
        value: 'permissionId',
        emitPath: false,
        checkStrictly: true
      },
      // 校验规则
      rules: {
        title: [{ required: true, message: '权限标题 必须填写', trigger: 'blur' }],
        parentId: [{ required: true, message: '上级权限 必须填写', trigger: 'blur' }],
        perms: [
          { required: true, message: '标识必须是全局唯一', trigger: 'blur' },
          {
            validator: function(rule, value, callback) {
              const regex = /[a-z]{2}$/
              if (!regex.test(value)) {
                callback(new Error('标识必须是全英文小写5位以上字母'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        sort: [{ required: true, message: '排序 必须填写', trigger: 'blur' }],
        permType: [{ required: true, message: '权限类型 必须填写', trigger: 'blur' }]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getPermissionTreeData()
    this.getMenusTreeData()
  },
  methods: {
    getPermissionTreeData() {
      // 获取权限列表以树节点的形式展示
      const _this = this
      _this.loading = true
      getPermissionTree({ filter: false }).then(result => {
        _this.treeData = result
        _this.loading = false
      })
    },
    getMenusTreeData() {
      // 获取权限列表以树节点的形式展示
      const _this = this
      _this.loading = true
      getPermissionTree({ filter: true }).then(result => {
        _this.menuTreeData = result
        _this.menuTreeData.push({ permissionId: 0, title: '顶级菜单', parentId: 0 })
        _this.loading = false
      })
    },
    handleNodeClick(node) {
      this.pmnForm = node
      this.isEdit = true
    },
    addEntity() {
      this.pmnForm = {
        permissionId: '',
        title: '',
        parentId: '',
        perms: '',
        icon: '',
        sort: '',
        permType: ''
      }
      this.isEdit = false
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.indexOf(value) !== -1
    },
    deleteEntity() {
      const _this = this
      if (_this.pmnForm.permissionId) {
        _this.$confirm(
          `确定要删除【 ${_this.pmnForm.title} 】吗? 请确认这个权限下面没有子权限.并且没有角色在使用这个权限.否则无法删除 是否继续?`,
          '警告',
          { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
          .then(() => {
            removePermissionById(_this.pmnForm.permissionId).then(result => {
              _this.$notify({ type: 'success', title: '成功', message: '删除权限成功!' })
              this.getPermissionTreeData()
              this.getMenusTreeData()
              _this.isEdit = true
            })
          })
      } else {
        _this.$notify.error({ title: '错误', message: '请先选中权限,才可以删除' })
      }
    },
    saveAndFlush() {
      const _this = this
      console.log('_this.pmnForm', _this.pmnForm)
      _this.$refs.pmnForm.validate(valid => {
        if (valid) {
          if (_this.pmnForm.permissionId) {
            updatePermission(_this.pmnForm).then(result => {
              _this.$notify({ title: '成功', message: '修改权限成功!', type: 'success' })
              this.getPermissionTreeData()
              this.getMenusTreeData()
              _this.isEdit = true
            })
          } else {
            savePermission(_this.pmnForm).then(result => {
              _this.$notify({ title: '成功', message: '新增权限成功!', type: 'success' })
              this.getPermissionTreeData()
              this.getMenusTreeData()
              _this.isEdit = true
            })
          }
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang='scss' scoped>
</style>

<style rel="stylesheet/scss" lang="scss">
#pmn {
  .el-tree-node__expand-icon {
    font-size: 1rem;
    font-weight: 600;
  }

  .el-tree-node__label {
    font-size: 1rem;
    font-weight: 600;
  }

  .el-tree-node__content {
    height: 2.5rem;
  }

  .el-alert__title {
    font-size: 1rem;
  }

  .el-input-number {
    width: 100%;
  }

  .el-select {
    width: 100%;
  }

  .el-cascader {
    width: 100%;
  }
}
</style>
