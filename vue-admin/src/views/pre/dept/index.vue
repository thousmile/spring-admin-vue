<!-- 部门管理 -->
<template>
  <el-container v-has="'pre_dept:view'" id="dept">
    <el-header>
      <div v-title>部门管理</div>
      <el-alert :title="website.dept.alert" type="info"/>
    </el-header>
    <el-main v-loading="loading">
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="grid-content bg-purple">
            <el-tree :data="treeData" :props="defaultProps" :default-expand-all="true" @node-click="handleNodeClick" />
          </div>
        </el-col>
        <el-col :span="16">

          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span class="card-header">部门详细信息</span>
              <el-button-group style="float: right;">
                <el-button v-has="'pre_dept:update'" type="primary" size="mini" icon="el-icon-edit" @click=" isEdit = !isEdit ">编辑本部门</el-button>
                <el-button v-has="'pre_dept:new'" type="primary" size="mini" icon="el-icon-share" @click="addEntity">添加子部门</el-button>
                <el-button v-has="'pre_dept:delete'" type="primary" size="mini" icon="el-icon-delete" @click="deleteEntity">删除本部门</el-button>
              </el-button-group>
            </div>

            <el-form ref="deptForm" :rules="rules" :model="deptForm" label-width="100px">
              <el-form-item label="部门ID">
                <el-input v-model="deptForm.id" :disabled="true"/>
              </el-form-item>
              <el-form-item label="部门名称" prop="name">
                <el-input :disabled="isEdit" v-model="deptForm.name" clearable />
              </el-form-item>
              <el-form-item label="上级部门" prop="parentId" >
                <el-select v-model="deptForm.parentId" :disabled="isEdit" placeholder="选择上级部门" >
                  <el-option v-for="item in listData" :key="item.id" :label="item.name" :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="部门排序" prop="level">
                <el-input :disabled="isEdit" v-model="deptForm.level" clearable />
              </el-form-item>
              <el-form-item label="部门描述" prop="describe">
                <el-input :disabled="isEdit" v-model="deptForm.describe" clearable />
              </el-form-item>
              <el-form-item v-if="isEdit === false">
                <el-button type="primary" @click="saveAndFlush">保存</el-button>
                <el-button @click=" isEdit = !isEdit ">取消</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>

import { getDeptTree, getDeptAll, saveDept, updateDept, removeDeptById } from '@/api/department'
import { mapGetters } from 'vuex'

export default {
  components: {},
  data() {
    return {
      deptForm: {
        id: 0,
        name: '',
        parentId: '',
        level: '',
        describe: ''
      },
      loading: false,
      isEdit: true, // 是否可以编辑
      treeData: [],
      listData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      // 校验规则
      rules: {
        name: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }],
        parentId: [{ required: true, message: '上级部门不能为空', trigger: 'blur' }],
        level: [{ required: true, message: '部门排序不能为空,且必须是数字', trigger: 'blur' }],
        describe: [{ required: true, message: '部门描述不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
    ...mapGetters([
      'website'
    ])
  },
  created() {
    this.getDeptTreeData()
    this.getDeptListData()
  },
  methods: {
    getDeptTreeData() {
      const _this = this
      _this.loading = true
      getDeptTree().then((result) => {
        if (result.status === 200) {
          _this.treeData = result.data
          _this.loading = false
        }
      }).catch((err) => {
        console.log('err :', err)
      })
    },
    getDeptListData() {
      const _this = this
      _this.loading = true
      getDeptAll().then((result) => {
        if (result.status === 200) {
          _this.listData = result.data
          _this.loading = false
        }
      }).catch((err) => {
        console.log('err :', err)
      })
    },
    handleNodeClick(data) {
      this.deptForm = data
    },
    handleChange(data) {
      console.log('data :', data)
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.deptForm.validate(valid => {
        if (valid) {
          _this.deptForm.upTime = new Date()
          if (_this.deptForm.id > 0) {
            updateDept(_this.deptForm).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '修改部门成功!', type: 'success' })
                _this.getDeptTreeData()
                _this.getDeptListData()
              }
              _this.isEdit = true
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          } else {
            _this.deptForm.addTime = new Date()
            saveDept(_this.deptForm).then((result) => {
              if (result.status === 200) {
                _this.$notify({ title: '成功', message: '新增部门成功!', type: 'success' })
                _this.getDeptTreeData()
                _this.getDeptListData()
              }
              _this.isEdit = true
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }
        }
      })
    },
    addEntity() {
      this.isEdit = false
      const parentId = this.deptForm.id
      this.deptForm = { id: 0, name: '', parentId: parentId, level: '', describe: '' }
    },
    deleteEntity() {
      const _this = this
      if (_this.deptForm.id > 0) {
        _this.$confirm('确定要删除【' + _this.deptForm.name + '】吗? 请确认这个部门下面没有用了.否则无法删除 是否继续?', '警告',
          { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
          .then(() => {
            removeDeptById(_this.deptForm.id).then((result) => {
              if (result.status === 200) {
                _this.$notify({ type: 'success', title: '成功', message: '删除部门成功!' })
                _this.getDeptTreeData()
                _this.getDeptListData()
              }
            }).catch((err) => {
              console.log('err :', err)
              _this.$notify.error({ title: '错误', message: err.message })
            })
          }).catch(() => {
            _this.$message({ type: 'info', message: '已取消删除' })
          })
      } else {
        _this.$notify.error({ title: '错误', message: '请先选中部门,才可以删除' })
      }
    }
  }
}

</script>
<style lang='scss' scoped>
.card-header{
  font-size: 1.5rem;
  font-weight: 600;
}
</style>

<style>
#dept .el-tree-node__expand-icon,
#dept .el-tree-node__label {
  font-size: 1.5rem;
}

#dept .el-tree-node__content {
  height: 2.5rem;
}

#dept .el-alert__title{
  font-size: 1rem;
}

</style>
