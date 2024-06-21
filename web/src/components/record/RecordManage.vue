<template>
  <div>
    <!--搜索栏-->
    <div style="margin-bottom: 5px">
      <el-input
          v-model="name"
          placeholder="请输入物品名"
          suffix-icon="el-icon-search"
          style="width: 200px;"
          @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 10px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 10px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>

    </div>

    <!--记录列表-->
    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border
      >
        <el-table-column prop="id" label="ID">
        </el-table-column>
        <el-table-column prop="goodsname" label="物品名">
        </el-table-column>
        <el-table-column prop="storagename" label="仓库">
        </el-table-column>
        <el-table-column prop="goodstypename" label="分类">
        </el-table-column>
        <el-table-column prop="adminname" label="操作人">
        </el-table-column>
        <el-table-column prop="username" label="申请人">
        </el-table-column>
        <el-table-column prop="count" label="数量">
        </el-table-column>
        <el-table-column prop="remark" label="备注">
        </el-table-column>
      </el-table>
    </div>

    <!--分页栏-->
    <div>
      <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageTotal">
      </el-pagination>
    </div>

  </div>
</template>


<script>
export default {
  name: "RecordManage",
  data() {
    return {
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目
      name: '', // 搜索框用到的物品名
      storage: null, // 搜索框用到的仓库名
      goodstype: null, // 搜索框用到的分类名
      storageData: [], // 物品所属仓库
      goodstypeData: [], // 物品所属分类
    }
  },
  methods: {
    loadPost() { // 从后端获取数据
      this.$axios.post("record/listPage", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          goodstype: this.goodstype + "",
          storage: this.storage + ""
        }
      }).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.tableData = res.data
          this.pageTotal = res.total
        } else {
          alert('获取数据失败')
        }
      })
    },
    resetParam() { // 重置搜索框
      this.name = null
      this.storage = null
      this.goodstype = null
      this.loadPost()
    },
    loadStorage() { // 获取所有仓库
      this.$axios.get("storage/list").then(res => res.data).then(res => {
        if (res.code === 200) {
          this.storageData = res.data
        } else {
          alert('获取数据失败')
        }
      })
    },
    formatStorage(row) { // 获取仓库名
      let temp = this.storageData.find(item => {
        return item.id === row.storage
      })
      return temp.name
    },
    loadGoodsType() { // 获取所有物品分类
      this.$axios.get("goodstype/list").then(res => res.data).then(res => {
        if (res.code === 200) {
          this.goodstypeData = res.data
        } else {
          alert('获取数据失败')
        }
      })
    },
    formatGoodsType(row) { // 获取分类名
      let temp = this.goodstypeData.find(item => {
        return item.id === row.goodstype
      })
      return temp.name
    },
    handleSizeChange(val) { //更改每页条数
      this.pageNum = 1
      this.pageSize = val
      this.loadPost()
    },
    handleCurrentChange(val) { //更改当前页数
      this.pageNum = val
      this.loadPost()
    }
  }
  ,
  beforeMount() { // 页面加载前
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>