<template>
  <div class="Header">
    <i :class="icon" @click="collapse"></i>
    <span class="title">欢迎来到仓库管理系统</span>
    <el-dropdown>
      <span class="dropdown">
        <span>{{ user.name }}</span>
        <i class="el-icon-arrow-down"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "GlobalHeader",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props: {
    icon: String
  },
  methods: {
    collapse() { //侧边栏
      this.$emit('doCollapse')
    },
    toUser() {
      this.$router.push('/Home')
    },
    exit() {
      this.$confirm('确认退出？', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        center: 'true'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '退出成功'
        })
        sessionStorage.clear()
        this.$router.push('/')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消退出'
        })
      })
    }
  }
}
</script>

<style scoped>
.Header {
  display: flex;
  line-height: 50px;
}

i:first-child {
  font-size: 22px;
  line-height: 55px;
  cursor: pointer;
}

.title {
  flex: 1;
  text-align: center;
  font-size: 25px;
}

i:nth-child(2) {
  margin-left: 5px;
}

.dropdown {
  margin-right: 18px;
}
</style>