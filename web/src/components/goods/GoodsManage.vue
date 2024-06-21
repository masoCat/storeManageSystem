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
      <el-button type="warning" style="margin-left: 10px;" @click="add">新增</el-button>


      <el-button type="primary" style="float: right; margin-right: 3%;" @click="inOutGoods(2)">出库</el-button>
      <el-button type="primary" style="float: right; margin-right: 1%;" @click="inOutGoods(1)">入库</el-button>
    </div>

    <!--物品列表-->
    <div>
      <el-table :data="tableData"
                :header-cell-style="{background: '#8b8888',color:'#ffffff'}"
                border
                highlight-current-row
                @current-change="selectCurrentChange">
        <el-table-column prop="id" label="ID" width="180%">
        </el-table-column>
        <el-table-column prop="name" label="物品名" width="180%">
        </el-table-column>
        <el-table-column prop="storage" label="仓库" :formatter="formatStorage">
        </el-table-column>
        <el-table-column prop="goodstype" label="分类" :formatter="formatGoodsType">
        </el-table-column>
        <el-table-column prop="count" label="数量">
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
        <el-form-item label="物品名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="storage">
          <el-col :span="20">
            <el-select v-model="form.storage" placeholder="请选择仓库" style="margin-left: 10px">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="分类" prop="goodstype">
          <el-col :span="20">
            <el-select v-model="form.goodstype" placeholder="请选择分类" style="margin-left: 10px">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form.count"></el-input>
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


    <!--入库模态框-->
    <el-dialog
        title="入库"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="70%"
          title="入库申请人"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="confirmUser">确 定</el-button>
          <el-button @click="innerVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <el-form ref="formInOut" :rules="rulesIn" :model="formInOut" label-width="80px">
        <el-form-item label="物品名">
          <el-col :span="20">
            <el-input v-model="formInOut.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人">
          <el-col :span="20">
            <el-input v-model="formInOut.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="formInOut.count"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="formInOut.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doInOutGoods">确 定</el-button>
        <el-button @click="inDialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import SelectUser from "@/components/user/SelectUser";

export default {
  components: {SelectUser},
  name: "GoodsManage",
  data() {
    //检验物品重复
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios.get("goods/findByName?name=" + this.form.name).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback()
        } else {
          callback(new Error('物品已经存在'));
        }
      })
    };
    // 检验物品数量
    let checkCount = (rule, value, callback) => {
      if (value > 9999) {
        callback(new Error('数量输入过大'));
      } else {
        callback();
      }
    }
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
      tableData: [], // 表格主体内容
      pageSize: 5, // 一页显示条目
      pageNum: 1, // 当前页数
      pageTotal: 0, // 总条目

      name: '', // 搜索框用到的物品名
      storage: null, // 搜索框用到的仓库名
      goodstype: null, // 搜索框用到的分类名
      storageData: [], // 物品所属仓库
      goodstypeData: [], // 物品所属分类

      centerDialogVisible: false, // 模态框显示
      inDialogVisible: false, // 入库模态框显示
      innerVisible: false, // 申请人模态框显示

      currentRow: {}, // 点击物品后选中的数据
      tempUser: {}, // 在申请人模态框点击用户后选中的数据
      form: { // 模态框中表单
        id: null,
        name: null,
        storage: null,
        goodstype: null,
        count: null,
        remark: null
      },
      rules: { // 检验
        name: [
          {required: true, message: "请输入物品名", trigger: 'blur'},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        storage: [
          {required: true, message: "请选择仓库", trigger: 'blur'},
        ],
        goodstype: [
          {required: true, message: "请选择分类", trigger: 'blur'},
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数字', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      },
      formInOut: { // 入库表单
        goods: '',
        goodsname: '',
        count: '',
        username: '',
        userid: '',
        adminId: '',
        remark: '',
        action: '',
      },
      rulesIn: { // 入库表单校验
        username: [
          {required: true, message: "请选择入库申请人", trigger: 'blur'},
        ],
        count: [
          {required: true, message: '请输入数量', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数字', trigger: 'blur'},
          {validator: checkCount, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    loadPost() { // 从后端获取数据
      this.$axios.post("goods/listPage", {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          goodstype: this.goodstype + "",
          storage: this.storage + ""
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
        this.form.storage = row.storage
        this.form.goodstype = row.goodstype
        this.form.count = row.count
        this.form.remark = row.remark
      })
    },
    selectCurrentChange(val) { // 单选某一行
      this.currentRow = val;
    },
    resetInForm() { // 重置入库模态框
      this.$refs.formInOut.resetFields();
    },
    inOutGoods(action) { // 打开出入库模态框
      if (!this.currentRow.id) {
        alert("请选择记录");
        return
      }

      this.inDialogVisible = true
      this.$nextTick(() => {
        this.resetInForm()
      })

      this.formInOut.goods = this.currentRow.id
      this.formInOut.goodsname = this.currentRow.name
      this.formInOut.adminId = this.user.id
      this.formInOut.action = action
    },
    selectUser() { // 弹出模态框，用于选择入库的申请人
      this.innerVisible = true
    },
    doSelectUser(val) { // 从申请人模态框获得数据
      this.tempUser = val
    },
    confirmUser() { // 确认选择入库的申请人
      this.formInOut.userid = this.tempUser.id
      this.formInOut.username = this.tempUser.name
      this.innerVisible = false
    },
    doInOutGoods() { // 提交出入库
      let action = ""
      if (this.formInOut.action === 1) {
        action = "入库"
      } else {
        action = "出库"
      }
      this.formInOut.remark = action + "：" + this.formInOut.remark
      this.$axios.post("record/save", this.formInOut).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: action + '成功',
            type: 'success'
          })
          this.inDialogVisible = false
          this.loadPost()
          this.resetInForm()
        } else {
          this.$message({
            message: action + '失败',
            type: 'error'
          })
        }
      })
    },
    del(id) { // 删除
      this.$axios.get("goods/delete?id=" + id).then(res => res.data).then(res => {
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
    saveOrMod() { // 添加或修改物品
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$axios.post("goods/saveOrMod", this.form).then(res => res.data).then(res => {
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
    this.loadStorage();
    this.loadGoodsType();
    this.loadPost();
  }
}
</script>

<style scoped>

</style>