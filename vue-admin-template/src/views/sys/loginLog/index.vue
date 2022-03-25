<template>
  <el-container class="wrapper">
    <el-header>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            v-model="page.keywords"
            clearable
            placeholder="请输入关键字"
          />
        </el-col>
        <el-col :span="8">
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getTableData"
          >搜索
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="deleteEntity"
          >批量删除
          </el-button>
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
        @selection-change="tableSelectionChange"
      >
        <el-table-column type="selection" width="55"/>
        <el-table-column fixed prop="messageId" label="消息ID"/>
        <el-table-column prop="grantType" label="登录方式"/>
        <el-table-column prop="userId" label="用户ID"/>
        <el-table-column prop="nickname" label="昵称"/>
        <el-table-column prop="username" label="用户名"/>
        <el-table-column label="头像" width="80">
          <template slot-scope="scope">
            <div
              class="avatar-wrapper"
              @click="viewBigAvatar(scope.row.avatar)"
            >
              <img :src="scope.row.avatar" class="user-avatar">
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="requestIp" label="请求地址"/>
        <el-table-column prop="address" label="真实地址"/>
        <el-table-column prop="osName" label="操作系统"/>
        <el-table-column prop="browser" label="浏览器"/>
        <el-table-column prop="createTime" label="登录时间"/>
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
import { getLoginLogPage, removeLoginLogById } from '@/api/sysLog'

export default {
  name: 'LoginLog',
  data() {
    return {
      page: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        total: 0
      },
      tableData: [],
      selectionData: [],
      loading: false
    }
  },
  created() {
    this.getTableData()
  },
  methods: {
    getTableData() {
      const _this = this
      _this.loading = true
      getLoginLogPage(_this.page).then((result) => {
        _this.tableData = result.list
        _this.page.total = result.total
        _this.loading = false
      })
    },
    currentChange(pageNum) {
      this.page.pageNum = pageNum
      this.getTableData()
    },
    tableSelectionChange(items) {
      const keys = []
      for (const index in items) {
        keys.push(items[index].messageId)
      }
      this.selectionData = keys
    },
    deleteEntity() {
      let _this = this
      if (_this.selectionData) {
        let msg = ''
        _this.selectionData.forEach((item, index) => {
          msg += `<strong style="color: red;">${item}</strong><br>`
        })
        _this.$confirm(`确定要批量删除 <br>${msg} 吗?`, '提示', {
          dangerouslyUseHTMLString: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeLoginLogById(_this.selectionData).then(() => {
            _this.$notify({
              title: '成功',
              message: '删除登录日志成功!',
              type: 'success'
            })
            _this.getTableData()
          })
        }).catch(() => console.log('取消'))
      }
    }
  }
}
</script>

<style lang='scss' scoped>

</style>
