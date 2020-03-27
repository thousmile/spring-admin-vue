<template>
  <div
    id="chinaArea"
    v-loading="loading"
    element-loading-text="第一次加载了全部数据...所以会慢...之后本地有缓存了就会很快了..."
    element-loading-spinner="el-icon-loading"
  >
    <el-row :gutter="20">
      <el-col :span="10">
        <div class="grid-content bg-purple">
          <el-tree
            ref="tree"
            node-key="areaCode"
            highlight-current
            :data="treeData"
            :props="defaultProps"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>详情</span>
          </div>
          <div class="text item">
            行政代码: {{ chinaArea.areaCode }}
          </div>
          <div class="text item">
            级别: {{ chinaArea.level }}
          </div>
          <div class="text item">
            父级行政代码: {{ chinaArea.parentCode }}
          </div>
          <div class="text item">
            邮政编码: {{ chinaArea.zipCode }}
          </div>
          <div class="text item">
            区号: {{ chinaArea.cityCode }}
          </div>
          <div class="text item">
            名称: {{ chinaArea.name }}
          </div>
          <div class="text item">
            简称: {{ chinaArea.shortName }}
          </div>
          <div class="text item">
            组合名: {{ chinaArea.mergerName }}
          </div>
          <div class="text item">
            拼音: {{ chinaArea.pinyin }}
          </div>
          <div class="text item">
            经度: {{ chinaArea.lng }}
          </div>
          <div class="text item">
            纬度: {{ chinaArea.lat }}
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import { getChinaAreaAll, getChinaAreaTree } from '@/api/chinaArea'

export default {
  name: 'ChinaArea',
  components: {},
  data() {
    return {
      chinaArea: {
        areaCode: 0,
        level: '',
        parentCode: '',
        zipCode: '',
        cityCode: '',
        name: '',
        shortName: '',
        mergerName: '',
        pinyin: '',
        lng: '',
        lat: ''
      },
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      treeData: [],
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
      let data = JSON.parse(localStorage.getItem('chinaArea'))
      if (data === undefined || data === null || data.length < 1) {
        getChinaAreaTree()
          .then(result => {
            _this.loading = false
            _this.treeData = result
            localStorage.setItem('chinaArea', JSON.stringify(result))
          })
      } else {
        this.treeData = data
        _this.loading = false
      }
    },
    loadNode(node, resolve) {
      const _this = this
      _this.loading = true
      if (node.data.areaCode !== undefined && node.data.areaCode !== null) {
        _this.chinaArea = {
          areaCode: node.data.areaCode,
          level: node.data.level,
          parentCode: node.data.parentCode,
          zipCode: node.data.zipCode,
          cityCode: node.data.cityCode,
          name: node.data.name,
          shortName: node.data.shortName,
          mergerName: node.data.mergerName,
          pinyin: node.data.pinyin,
          lng: node.data.lng,
          lat: node.data.lat
        }
      }
      if (node.level === 0) {
        return resolve(_this.treeData)
      } else {
        getChinaAreaAll(node.key)
          .then(result => {
            _this.loading = false
            if (result === undefined || result === null || result.length < 1) {
              return resolve([])
            } else {
              return resolve(result)
            }
          })
      }
    },
    handleNodeClick(data) {
      this.chinaArea = data.source
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
#chinaArea {
  .el-tree-node__expand-icon {
    font-size: 1rem;
    font-weight: 600;
  }
  .el-tree-node__label {
    font-size: 1rem;
    font-weight: 600;
  }
  .el-tree-node__content {
    height: 1.5rem;
  }
  .el-alert__title {
    font-size: 1rem;
  }
  .text {
    font-size: 1rem;
  }

  .item {
    padding: 10px 0;
  }
}
</style>

<style lang='scss' scoped>

</style>
