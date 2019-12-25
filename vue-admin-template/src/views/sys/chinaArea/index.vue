<template>
  <div id="chinaArea">
    <div v-title>中国行政地区</div>
    <el-row :gutter="20">
      <el-col :span="10">
        <div class="grid-content bg-purple">
          <el-tree
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

import { getChinaAreaTree } from '@/api/chinaArea'

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
    this.getTableDate()
  },
  methods: {
    getTableDate() {
      const _this = this
      _this.loading = true
      getChinaAreaTree()
        .then(result => {
          _this.treeData = result
          _this.loading = false
        })
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
    font-size: 1.5rem;
  }
  .el-tree-node__label {
    font-size: 1rem;
  }
  .el-tree-node__content {
    height: 1.5rem;
  }
  .el-alert__title {
    font-size: 1rem;
  }
  .text {
    font-size: 2rem;
  }

  .item {
    padding: 10px 0;
  }
}
</style>

<style lang='scss' scoped>

</style>
