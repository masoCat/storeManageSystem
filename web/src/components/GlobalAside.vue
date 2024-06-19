<template>
  <el-menu class="aside"
           background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           default-active="/Home"
           :collapse="isCollapse"
           :collapse-transition="false">

    <el-menu-item index="/Home">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item index="/Home1">
      <i class="el-icon-s-management"></i>
      <span slot="title">导航1</span>
    </el-menu-item>

    <el-menu-item index="/Home2">
      <i class="el-icon-s-data"></i>
      <span slot="title">导航2</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  name: "GlobalAside",
  props: {
    isCollapse: Boolean
  }
}
</script>

<style scoped>
.aside {
  height: 100%;
}
</style>