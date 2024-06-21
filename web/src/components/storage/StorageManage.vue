<template>
  <div>
    <!--搜索栏-->
    <div style="margin-bottom: 5px">
      <el-input
          v-model="name"
          placeholder="请输入仓库名"
          suffix-icon="el-icon-search"
          style="width: 200px;"
          @keyup.enter.native="loadPost"></el-input>

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
        <el-table-column prop="id" label="ID" width="180%">
        </el-table-column>
        <el-table-column prop="name" label="仓库名" width="180%">
        </el-table-column>
        <el-table-column prop="remark" label="备注">
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
        <el-form-item label="仓库名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
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
  name: "StorageManage",
  data() {
    //检验仓库重复
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get("storage/findByName?name=" + this.form.name).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback()
        } else {
          callback(new Error('仓库已经存在'));
        }
      })
    };
    return {
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目
      name: '', // 搜索时用到的仓库名
      centerDialogVisible: false, // 模态框显示
      form: { // 模态框中表单
        id: null,
        name: null,
        remark: null
      },
      rules: { // 检验
        name: [
          {required: true, message: "请输入仓库名", trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    loadPost() { // 从后端获取数据
      this.$axios.post("storage/listPage", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name
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
      this.loadPost()
    },
    resetForm() { // 重置模态框
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
        this.form.name = row.name
        this.form.remark = row.remark
      })
    },
    del(id) { // 删除
      this.$axios.get("storage/delete?id=" + id).then(res => res.data).then(res => {
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
    saveOrMod() { // 添加或修改仓库
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post("storage/saveOrMod", this.form).then(res => res.data).then(res => {
            let action = this.form.id != null ? "修改" : "添加"
            if (res.code === 200) {
              this.$message({
                message: action + "成功",
                type: "success"
              })
              this.centerDialogVisible = false
              this.loadPost()
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