<template>
  <div>

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
      <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>

    </div>

    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border
      >
        <el-table-column prop="id" label="ID" width="120%">
        </el-table-column>
        <el-table-column prop="no" label="账号" width="150%">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="150%">
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="120%">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="120%">
          <template slot-scope="scope">
            <el-tag :type="(scope.row.sex === '男' ? 'primary' : 'success')">{{ scope.row.sex }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="roleId" label="角色" width="120%">
          <template slot-scope="scope">
            <el-tag
                :type="(scope.row.roleId === '超级管理员' ? 'danger' : (scope.row.roleId === '管理员' ? 'primary' : 'success'))">
              {{ scope.row.roleId }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="200%">
        </el-table-column>
        <el-table-column prop="operate" label="操作">
          <el-button size="small" type="success">编辑</el-button>
          <el-button size="small" type="danger">删除</el-button>
        </el-table-column>
      </el-table>
    </div>

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
    <el-dialog
        title="新增用户"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="账号">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="centerDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "GlobalMain",
  data() {
    return {
      tableData: [],
      pageSize: 5,
      pageNum: 1,
      pageTotal: 0,
      name: '',
      sex: '',
      sexs: [{
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }],
      centerDialogVisible: false,
      form: {
        no: null,
        name: null,
        password: null,
        age: null,
        sex: "1",
        phone: null,
        roleId: "2"
      }
    }
  },
  methods: {
    loadGet() {
      this.$axios.get("user/list").then(res => res.data).then(res => {
        this.tableData = res;
      })
    },
    loadPost() {
      this.$axios.post("user/listPageSuc", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex
        }
      }).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.tableData = res.data;
          this.tableData.forEach(item => {
            item.sex = (parseInt(item.sex) === 1 ? "男" : "女");
            item.roleId = (parseInt(item.roleId) === 0 ? "超级管理员" : (parseInt(item.roleId) === 1 ? "管理员" : "用户"));
          });
          this.pageTotal = res.total
          console.log(res)
        } else {
          alert('获取数据失败')
        }
      })
    },
    add() {
      this.centerDialogVisible = true
    },
    save() {
      this.$axios.post("user/save", this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message:"添加成功",
            type:"success"
          })
          this.centerDialogVisible = false
          this.loadPost()
        } else {
          this.$message({
            message:"添加失败",
            type:"error"
          })
        }
      })
    },
    resetParam() {
      this.name = ''
      this.sex = ''
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1
      this.pageSize = val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val
      this.loadPost()
    }
  },
  beforeMount() {
    // this.loadGet();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>