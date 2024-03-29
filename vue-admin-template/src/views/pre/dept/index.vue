<!-- 部门管理 -->
<template>
  <el-container id="dept" v-has="'pre_dept:view'" class="wrapper">
    <el-header>
      <el-alert
        title="请勿乱删除部门, 确实需要删除部门的时候, (1).请确定这个部门下面没有子部门, (2).请确定这个部门下面没有用户. 只有满足以上两种情况才可以删除部门成功!"
        type="warning"
      />
    </el-header>
    <el-main v-loading="loading" element-loading-text="拼命加载中..." element-loading-spinner="el-icon-loading">
      <el-row :gutter="20">
        <el-col :span="10">
          <el-input v-model="filterText" clearable placeholder="输入关键字进行过滤">
            <i slot="prefix" class="el-input__icon el-icon-search" />
          </el-input>
          <br>
          <br>
          <div class="grid-content bg-purple">
            <el-tree
              ref="tree"
              :data="treeData"
              :props="defaultProps"
              :filter-node-method="filterNode"
              :default-expand-all="true"
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>
        <el-col :span="14">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span class="card-header">部门详细信息</span>
              <el-button-group style="float: right;">
                <el-button v-has="'pre_dept:update:permissions'" type="primary" icon="el-icon-share" @click="addEntity">修改权限
                </el-button>

                <el-button v-has="'pre_dept:create'" type="success" icon="el-icon-share" @click="addEntity">新增
                </el-button>

                <el-button v-has="'pre_dept:update'" type="warning" icon="el-icon-edit" @click="isEdit = !isEdit">修改
                </el-button>

                <el-button v-has="'pre_dept:delete'" type="danger" icon="el-icon-delete" @click="deleteEntity">删除
                </el-button>

              </el-button-group>
            </div>

            <el-form ref="deptForm" :rules="rules" :model="deptForm" label-width="100px">
              <el-form-item v-if="deptForm.deptId" label="部门ID">
                <el-input v-model="deptForm.deptId" :disabled="true" />
              </el-form-item>
              <el-form-item label="部门名称" prop="deptName">
                <el-input v-model="deptForm.deptName" :disabled="isEdit" clearable />
              </el-form-item>
              <el-form-item label="上级部门" prop="parentId">
                <el-cascader
                  v-model="deptForm.parentId"
                  :disabled="isEdit"
                  :show-all-levels="false"
                  :options="treeData"
                  clearable
                  filterable
                  :props="cascaderProps"
                />
              </el-form-item>
              <el-form-item label="部门排序" prop="sort">
                <el-input-number v-model="deptForm.sort" :disabled="isEdit" :min="1" :max="10000" label="部门排序" />
              </el-form-item>
              <el-form-item label="部门领导" prop="leader">
                <el-input v-model="deptForm.leader" :disabled="isEdit" clearable />
              </el-form-item>
              <el-form-item label="领导电话" prop="leaderMobile">
                <el-input v-model="deptForm.leaderMobile" :disabled="isEdit" clearable />
              </el-form-item>
              <el-form-item label="部门描述" prop="description">
                <el-input
                  v-model="deptForm.description"
                  :disabled="isEdit"
                  :autosize="{ minRows: 3, maxRows: 10 }"
                  type="textarea"
                  clearable
                />
              </el-form-item>
              <el-form-item v-if="isEdit === false">
                <el-button type="primary" @click="saveAndFlush">保存</el-button>
                <el-button @click="isEdit = !isEdit">取消</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>

import { getDeptTree, saveDept, updateDept, removeDeptById } from '@/api/sysDept'
import { validatePhone } from '@/utils/validate'

export default {
  name: 'Dept',
  data() {
    return {
      deptForm: {
        deptId: '',
        parentId: '',
        deptName: '',
        leader: '',
        leaderMobile: '',
        sort: '',
        description: ''
      },
      filterText: '',
      loading: false,
      isEdit: true, // 是否可以编辑
      treeData: [],
      defaultLevel: 1,
      defaultProps: {
        children: 'children',
        label: 'deptName'
      },
      cascaderProps: {
        children: 'children',
        label: 'deptName',
        value: 'deptId',
        emitPath: false,
        checkStrictly: true
      },
      // 校验规则
      rules: {
        parentId: [{ required: true, message: '上级部门必须填写', trigger: 'blur' }],
        deptName: [{ required: true, message: '部门名称必须填写', trigger: 'blur' }],
        leader: [{ required: true, message: '部门领导必须填写', trigger: 'blur' }],
        leaderMobile: [
          { required: true, message: '领导联系方式必须填写', trigger: 'blur' },
          { required: true, validator: validatePhone, trigger: 'blur' }
        ],
        sort: [{ required: true, message: '部门排序必须填写,且必须是数字', trigger: 'blur' }],
        description: [{ required: true, message: '部门描述必须填写', trigger: 'blur' }]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getDeptTreeData()
  },
  methods: {
    getDeptTreeData() {
      const _this = this
      _this.loading = true
      getDeptTree().then(result => {
        _this.treeData = result
        _this.loading = false
      })
    },
    handleNodeClick(data) {
      this.isEdit = true
      this.deptForm = data
      if (data.children !== undefined) {
        this.defaultLevel = data.children.length + 1
      } else {
        this.defaultLevel = 1
      }
    },
    filterNode(value, data) {
      if (!value) return true
      return data.deptName.indexOf(value) !== -1
    },
    saveAndFlush() {
      const _this = this
      _this.$refs.deptForm.validate(valid => {
        if (valid) {
          // 修改部门信息
          if (_this.deptForm.deptId) {
            updateDept(_this.deptForm).then(result => {
              _this.$notify({ title: '成功', message: '修改部门成功!', type: 'success' })
              _this.getDeptTreeData()
              _this.isEdit = true
            })
          } else {
            // 新增部门
            saveDept(_this.deptForm).then(result => {
              _this.$notify({ title: '成功', message: '新增部门成功!', type: 'success' })
              _this.getDeptTreeData()
              _this.isEdit = true
            })
          }
        }
      })
    },
    addEntity() {
      this.isEdit = false
      const parentId = this.deptForm.deptId
      this.deptForm = {
        deptId: '',
        parentId: parentId,
        deptName: '',
        leader: '',
        leaderMobile: '',
        sort: this.defaultLevel,
        description: ''
      }
    },
    deleteEntity() {
      this.isEdit = true
      const _this = this
      if (_this.deptForm.id > 0) {
        _this.$confirm(
          `确定要删除【 ${_this.deptForm.deptName} 】吗? 请确认这个部门下面没用户.否则无法删除 是否继续?`,
          '警告',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          removeDeptById(_this.deptForm.deptId).then(result => {
            _this.$notify({
              type: 'success',
              title: '成功',
              message: '删除部门成功!'
            })
            _this.getDeptTreeData()
          })
        })
      } else {
        _this.$notify.error({
          title: '错误',
          message: '请先选中部门,才可以删除'
        })
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
#dept {
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

  .el-select {
    width: 100%;
  }

  .el-cascader {
    width: 100%;
  }

  .el-input-number {
    width: 100%;
  }
}
</style>
