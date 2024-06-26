<template>
  <el-menu class="aside"
           background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           default-active="/Home"
           :collapse="isCollapse"
           :collapse-transition="false"
           router>

    <el-menu-item index="/Home" @click="loadGet">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item @click="loadGet" :index="'/'+ item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">
          {{ item.menuname }}
      </span>
    </el-menu-item>

    <el-menu-item index="/Info" @click="loadGet">
      <i class="el-icon-s-comment"></i>
      <span slot="title">通知
        <el-badge v-if="info_num" :value="info_num" class="item" style="margin-bottom: 5px">
        </el-badge>
      </span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: "GlobalAside",
  data() {
    return {
      info_num: null
    }
  },
  computed: {
    "menu": {
      get() {
        return this.$store.state.menu
      }
    }
  },
  props: {
    isCollapse: Boolean
  },
  methods: {
    loadGet() { // 从后端获取数据
      this.$axios.get("info/list").then(res => res.data).then(res => {
        this.info_num = res
      })
    }
  },
  beforeMount() { // 页面加载前
    this.loadGet();
  }
}
</script>

<style scoped>
.aside {
  height: 100%;
}
</style>