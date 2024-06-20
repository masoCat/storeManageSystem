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
      <el-button type="primary" style="float: right; margin-right: 6%;" @click="add">新增</el-button>

    </div>

    <!--用户列表-->
    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border
      >
        <el-table-column prop="no" label="账号" width="180%">
        </el-table-column>
        <el-table-column prop="password" label="密码" width="180%">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="180%">
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
        <el-table-column prop="roleId" label="权限" width="100%">
          <template slot-scope="scope">
            <el-tag
                :type="(scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success'))">
              {{ scope.row.roleId === 0 ? "超级管理员" : (scope.row.roleId === 1 ? "管理员" : "用户") }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="电话" width="200%">
        </el-table-column>
        <el-table-column prop="operate" label="操作" width="180%">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
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

    <!--模态框-->
    <el-dialog
        title="新增或修改"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
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
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveOrMod">确 定</el-button>
        <el-button @click="centerDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
export default {
  name: "AdminManage",
  data() {
    //检验年龄大小
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error("年龄过大"));
      } else {
        callback();
      }
    };
    //检验账户重复
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get("user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback()
        } else {
          callback(new Error('账号已经存在'));
        }
      })
    };
    return {
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目
      name: '',
      sex: '',
      sexs: [{ // 性别集
        value: '1',
        label: '男'
      }, {
        value: '0',
        label: '女'
      }],
      centerDialogVisible: false, // 模态框显示
      form: { // 模态框中表单
        id: null,
        no: null, // 账号
        name: null,
        password: null,
        age: null,
        sex: "0",
        phone: null,
        roleId: "1" // 权限
      },
      rules: { // 检验
        no: [
          {required: true, message: "请输入账号", trigger: 'blur'},
          {min: 3, max: 8, message: "长度在3到8个字符", trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        name: [
          {required: true, message: "请输入名字", trigger: 'blur'},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: 'blur'},
          {min: 3, max: 8, message: "长度在3到8个字符", trigger: 'blur'}
        ],
        age: [
          {required: true, message: "请输入年龄", trigger: 'blur'},
          {min: 1, max: 3, message: "长度在1到3个位", trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: "年龄必须为正整数", trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true, message: "手机号不能为空", trigger: 'blur'},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: 'blur'}
        ]
      }
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
          roleId: '1'
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
    resetForm() { // 重置添加模态框
      this.$refs.form.resetFields();
    },
    add() { // 打开添加模态框
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm()
        this.form.id = null
      })
    },
    mod(row) { // 打开修改模态框
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.resetForm()
        this.form.id = row.id
        this.form.no = row.no // 账号
        this.form.name = row.name
        this.form.password = row.password
        this.form.age = row.age + ""
        this.form.sex = row.sex + ""
        this.form.phone = row.phone
        this.form.roleId = row.roleId + "" // 权限
      })
    },
    del(id) { // 打开删除模态框
      this.$axios.get("user/delete?id=" + id).then(res => res.data).then(res => {
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
    saveOrMod() { // 添加或修改用户
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post("user/saveOrMod", this.form).then(res => res.data).then(res => {
            let action = this.form.id != null ? "修改" : "添加"
            if (res.code === 200) {
              this.$message({
                message: action + "成功",
                type: "success"
              })
              this.centerDialogVisible = false
              this.loadPost()
              this.resetForm()
            } else {
              this.$message({
                message: action + "失败",
                type: "error"
              })
            }
          })
        } else {
          return false
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