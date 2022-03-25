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

    <el-dialog
      title="详情"
      :visible.sync="dialogVisible"
    >
      <strong>请求参数:</strong>
      <div class="jsonEditor">
        <vue-json-editor
          v-model="infoData.methodArgs"
          :mode="'code'"
          lang="zh"
        />
      </div>
      <br>
      <br>
      <strong>响应结果:</strong>
      <div class="jsonEditor">
        <vue-json-editor
          v-model="infoData.responseResult"
          :mode="'code'"
          lang="zh"
        />
      </div>
      <br>
      <strong>错误日志:</strong>
      <div>
        {{ infoData.errorLog }}
      </div>
    </el-dialog>

    <el-main>
      <el-table
        v-loading="loading"
        element-loading-text="拼命加载中..."
        element-loading-spinner="el-icon-loading"
        :data="tableData"
        border
        @selection-change="tableSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column fixed prop="messageId" label="消息ID" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="operType" label="操作类型" />
        <el-table-column prop="userId" label="用户ID" />
        <el-table-column prop="method" label="方法名称" />
        <el-table-column prop="requestUrl" label="URL" />
        <el-table-column prop="requestIp" label="IP" />
        <el-table-column prop="address" label="真实地址" />
        <el-table-column prop="status" label="操作状态" />
        <el-table-column prop="timeCost" label="耗时(毫秒)" />
        <el-table-column prop="createTime" label="登录时间" />
        <el-table-column label="操作人">
          <template slot-scope="scope">
            <show-user-avatar :user-id="scope.row.userId" />
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-link icon="el-icon-view" @click="viewInfoText(scope.row)">详情</el-link>
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
import { getOperLogPage, removeOperLogById } from '@/api/sysLog'
import vueJsonEditor from 'vue-json-editor'

export default {
  name: 'LoginLog',
  components: {
    vueJsonEditor
  },
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
      infoData: {},
      dialogVisible: false,
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
      getOperLogPage(_this.page).then((result) => {
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
    viewInfoText(data) {
      this.infoData = {
        responseResult: JSON.parse(data.responseResult),
        methodArgs: JSON.parse(data.methodArgs),
        errorLog: data.errorLog
      }
      this.dialogVisible = true
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
          removeOperLogById(_this.selectionData).then(() => {
            _this.$notify({
              title: '成功',
              message: '删除操作日志成功!',
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
.updateAttributes {
  height: 671px;
  padding: 20px;

  .jsonEditor {
    width: 600px;
    height: 400px;
  }
}
</style>
