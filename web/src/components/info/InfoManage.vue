<template>
  <div>
    <!--搜索栏-->
    <div style="margin-bottom: 5px">
      <el-input
          v-model="info"
          placeholder="请输入通知内容"
          suffix-icon="el-icon-search"
          style="width: 200px;"
          @keyup.enter.native="loadPost">
      </el-input>
      <el-select v-model="type" placeholder="请选择通知类型" style="margin-left: 10px">
        <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>

    <!--通知列表-->
    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border>
        <el-table-column prop="id" label="ID" width="100%">
        </el-table-column>
        <el-table-column prop="type" label="通知类型" width="100%">
          <template slot-scope="scope">
            <el-tag :type="(scope.row.type === 1 ? 'primary' : 'danger')"
                    disable-transitions>{{ scope.row.type === 1 ? "出入库申请" : "库存预警" }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="info" label="通知内容">
        </el-table-column>
        <el-table-column prop="operate" label="操作" width="100%">
          <template slot-scope="scope">
            <el-popconfirm title="确定删除？" @confirm="del(scope.row.id)" style="margin-left: 20px">
              <el-button slot="reference" size="small" type="danger">删除</el-button>
            </el-popconfirm>
          </template>
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
  name: "InfoManage",
  data() {
    return {
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目

      info: '', // 搜索框用到的通知内容
      type: null, // 搜索框用到的通知分类
      types: [{ // 搜索时选择的通知分类
        value: '0',
        label: '库存预警'
      }, {
        value: '1',
        label: '出入库申请'
      }]
    }
  },
  methods: {
    loadPost() { // 从后端获取数据
      this.$axios.post("info/listPage", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          info: this.info,
          type: this.type + ""
        }
      }).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.tableData = res.data;
          this.pageTotal = res.total
        } else {
          alert('获取数据失败')
        }
      })
    },
    resetParam() { // 重置搜索框
      this.info = null
      this.type = null
      this.loadPost()
    },
    del(id) { // 删除
      this.$axios.get("info/delete?id=" + id).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: "删除成功",
            type: "success"
          })
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          this.$message({
            message: "删除失败",
            type: "error"
          })
        }
      })
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
  },
  beforeMount() { // 页面加载前
    this.loadPost();
  }
}
</script>

<style scoped>

</style>