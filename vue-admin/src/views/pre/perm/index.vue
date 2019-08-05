<!--  -->
<template>
  <div v-loading="loading" v-has="'pre_perm:view'" id="pmn">
    <div v-title>权限管理</div>
    <el-row :gutter="20">
      <el-col :span="10">
        <el-tree :data="treeData" :props="defaultProps" accordion @node-click="handleNodeClick"/>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">

          <div slot="header" class="clearfix">
            <span class="card-header">权限详细信息</span>
            <el-button-group style="float: right;">
              <el-button v-has="'pre_perm:update'" type="primary" size="mini" icon="el-icon-edit" @click=" isEdit = !isEdit ">编辑本权限</el-button>
              <el-button v-has="'pre_perm:new'" type="primary" size="mini" icon="el-icon-share" @click="addEntity">添加子权限</el-button>
              <el-button v-has="'pre_perm:delete'" type="primary" size="mini" icon="el-icon-delete" @click="deleteEntity">删除本权限</el-button>
            </el-button-group>
          </div>

          <el-form ref="pmnForm" :rules="rules" :model="pmnForm" label-width="100px">
            <el-form-item label="权限ID">
              <el-input v-model="pmnForm.pid" :disabled="true"/>
            </el-form-item>

            <el-form-item label="权限标题" prop="title">
              <el-input :disabled="isEdit" v-model="pmnForm.title" clearable />
            </el-form-item>

            <template v-if="pmnForm.type === 'menu'">
              <el-form-item label="权限图标" prop="icon">
                <el-input :disabled="isEdit" v-model="pmnForm.icon" clearable />
              </el-form-item>
            </template>

            <el-form-item label="唯一标识" prop="resources">
              <el-input :disabled="isEdit" v-model="pmnForm.resources" clearable placeholder="强调，必须是唯一的英文字母" />
            </el-form-item>

            <el-form-item label="上级权限" prop="parentId" >
              <el-select v-model="pmnForm.parentId" :disabled="isEdit" filterable clearable placeholder="选择上级权限" >
                <el-option v-for="item in listDate" :key="item.pid" :label="item.title" :value="item.pid"/>
              </el-select>
            </el-form-item>

            <el-form-item label="权限类型" prop="type" >
              <el-select v-model="pmnForm.type" :disabled="isEdit" placeholder="选择类型" >
                <el-option v-for="item in pmnType" :key="item.type" :label="item.name" :value="item.type"/>
              </el-select>
            </el-form-item>

            <el-form-item label="权限描述" prop="describe">
              <el-input :disabled="isEdit" v-model="pmnForm.describe" clearable />
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

import { getPermissionTree, getPermissionAll, savePermission, updatePermission, removePermissionById } from '@/api/permission'

export default {
  components: {},
  data() {
    return {
      pmnForm: {
        pid: 0,
        icon: '',
        title: '',
        parentId: '',
        resources: '',
        describe: '',
        type: ''
      },
      loading: false,
      isEdit: true, // 是否可以编辑
      treeData: [],
      listDate: [],
      pmnType: [
        { type: 'button', name: '按钮' },
        { type: 'menu', name: '菜单' }
      ],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      // 校验规则
      rules: {
        title: [{ required: true, message: '权限标题不能为空', trigger: 'blur' }],
        parentId: [{ required: true, message: '上级权限不能为空', trigger: 'blur' }],
        resources: [{ required: true, message: '标识必须是全局唯一', trigger: 'blur' },
          { validator: function(rule, value, callback) {
            const regex = /[a-z]{2}$/
            if (!regex.test(value)) {
              callback(new Error('标识必须是全英文小写5位以上字母'))
            } else {
              callback()
            }
          }, trigger: 'blur' }
        ],
        describe: [{ required: true, message: '权限描述不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '权限类型不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getPermissionTreeData()
    this.getPermissionListData()
  },
  methods: {
    getPermissionTreeData() {
      // 获取权限列表以树节点的形式展示
      const _this = this
      _this.loading = true
      getPermissionTree().then((result) => {
        if (result.status === 200) {
          _this.treeData = result.data
          _this.loading = false
        }
      }).catch((err) => {
        console.log('err :', err)
      })
    },
    getPermissionListData() {
      // 获取权限列表以列表的形式展示
      const _this = this
      _this.loading = true
      getPermissionAll().then((result) => {
        if (result.status === 200) {
          _this.listDate = result.data
          _this.listDate.push({ pid: 0, title: '一级菜单' })
          _this.loading = false
        }
      }).catch((err) => {
        console.log('err :', err)
      })
    },
    handleNodeClick(data) {
      this.pmnForm = {
        pid: data.pid,
        icon: data.icon,
        title: data.title,
        parentId: data.parentId,
        resources: data.resources,
        describe: data.describe,
        createTime: data.createTime,
        type: data.type
      }
    },
    addEntity() {
      this.pmnForm = { pid: 0, icon: '', title: '', parentId: 0, resources: '', describe: '', type: 'menu' }
      this.isEdit = false
    },
    deleteEntity() {
      const _this = this
      if (_this.pmnForm.pid > 0) {
        _this.$confirm('确定要删除【' + _this.pmnForm.title + '】吗? 请确认这个权限下面没有子权限.并且没有角色在使用这个权限.否则无法删除 是否继续?', '警告',
          { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
          .then(() => {
            removePermissionById(_this.pmnForm.pid).then((result) => {
              if (result.status === 200) {
                _this.$notify({ type: 'success', title: '成功', message: '删除权限成功!' })
                _this.getPermissionTreeData()
                _this.getPermissionListData()
                _this.isEdit = true
              }
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }).catch(() => {
            _this.$message({ type: 'info', message: '已取消删除' })
          })
      } else {
        _this.$notify.error({ title: '错误', message: '请先选中权限,才可以删除' })
      }
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.pmnForm.validate(valid => {
        if (valid) {
          if (_this.pmnForm.pid > 0) {
            updatePermission(_this.pmnForm).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '修改权限成功!', type: 'success' })
                _this.getPermissionTreeData()
                _this.getPermissionListData()
              }
              _this.isEdit = true
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          } else {
            savePermission(_this.pmnForm).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '新增权限成功!', type: 'success' })
                _this.getPermissionTreeData()
                _this.getPermissionListData()
              }
              _this.isEdit = true
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }
        }
      })
    }
  }
}
</script>

<style lang='scss' scoped>

</style>

<style>
#pmn .el-tree-node__expand-icon,
#pmn .el-tree-node__label {
  font-size: 1.5rem;
}

#pmn .el-tree-node__content {
  height: 2.5rem;
}

#pmn .el-alert__title{
  font-size: 1rem;
}

</style>
