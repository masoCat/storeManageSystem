<template>
  <div>
    <!--搜索栏-->
    <div style="margin-bottom: 5px">
      <el-input
          v-model="name"
          placeholder="请输入名字"
          suffix-icon="el-icon-search"
          style="width: 200px;"
          @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="sex" placeholder="请选择性别" style="margin-left: 5px">
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>

    <!--用户列表-->
    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border
                highlight-current-row
                @current-change="selectCurrentChange">
        <el-table-column prop="no" label="账号">
        </el-table-column>
        <el-table-column prop="password" label="密码">
        </el-table-column>
        <el-table-column prop="name" label="姓名">
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="100%">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100%">
          <template slot-scope="scope">
            <el-tag :type="(scope.row.sex === 1 ? 'primary' : 'success')"
                    disable-transitions>{{ scope.row.sex === 1 ? "男" : "女" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="roleId" label="权限">
          <template slot-scope="scope">
            <el-tag
                :type="(scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success'))">
              {{ scope.row.roleId === 0 ? "超级管理员" : (scope.row.roleId === 1 ? "管理员" : "用户") }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话">
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
  name: "SelectUser",
  data() {
    return {
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目
      name: '', // 搜索时用到的姓名
      sex: '', // 搜索时用到的性别
      sexs: [{ // 性别集
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }],
    }
  },
  methods: {
    loadPost() { // 从后端获取数据
      this.$axios.post("user/listPageSuc", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex,
          roleId: '2'
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
      this.name = ''
      this.sex = ''
      this.loadPost()
    },
    selectCurrentChange(val) { // 单选某一行
      this.$emit("doSelectUser", val);
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