<template>
  <div class="Header">
    <i :class="icon" @click="collapse"></i>
    <span class="title">欢迎来到仓库管理系统</span>
    <el-dropdown>
      <span class="dropdown">
        <span>王小虎</span>
        <i class="el-icon-arrow-down"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="exit">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "GlobalHeader",
  props: {
    icon: String
  },
  methods: {
    toUser() {
      console.log("user");
    },
    exit() {
      console.log("exit");
    },
    collapse() {
      this.$emit('doCollapse')
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