<template>
  <el-table :data="list" style="width: 100%;padding-top: 15px;">
    <el-table-column label="Order_No" min-width="200">
      <template slot-scope="scope">
        {{ scope.row.order_no | orderNoFilter }}
      </template>
    </el-table-column>
    <el-table-column label="Price" width="195" align="center">
      <template slot-scope="scope">
        ¥{{ scope.row.price | toThousandFilter }}
      </template>
    </el-table-column>
    <el-table-column label="Status" width="100" align="center">
      <template slot-scope="{ row }">
        <el-tag :type="row.status | statusFilter">
          {{ row.status }}
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      list: null
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.list = [
        { order_no: '5648894549841', price: 100, status: 'success' },
        { order_no: '5648894592842', price: 250, status: 'danger' },
        { order_no: '5648894592843', price: 350, status: 'success' },
        { order_no: '5648894592844', price: 5650, status: 'danger' },
        { order_no: '5648894592845', price: 1570, status: 'success' },
        { order_no: '5648894592846', price: 5560, status: 'danger' },
        { order_no: '5648894592847', price: 5511, status: 'success' },
        { order_no: '5648894592848', price: 110, status: 'danger' },
        { order_no: '5648894592849', price: 99, status: 'success' },
        { order_no: '5648894592850', price: 1999, status: 'success' }
      ]
    }
  }
}
</script>
