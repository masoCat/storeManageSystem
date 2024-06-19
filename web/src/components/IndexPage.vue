<template>
  <el-container style="flex-grow: 1">
    <el-aside :width="aside_width">
      <GlobalAside :isCollapse="isCollapse"></GlobalAside>
    </el-aside>

    <el-container>
      <el-header>
        <GlobalHeader :icon="icon" @doCollapse="doCollapse"></GlobalHeader>
      </el-header>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import GlobalAside from "@/components/global/GlobalAside";
import GlobalHeader from "@/components/global/GlobalHeader";

export default {
  name: "IndexPage",
  components: {
    GlobalAside,
    GlobalHeader
  },
  data() {
    return {
      isCollapse: false,
      aside_width: "300px",
      icon: "el-icon-s-fold"
    }
  },
  methods: {
    doCollapse() {
      this.isCollapse = !this.isCollapse;
      this.aside_width = this.isCollapse ? "63px" : "300px";
      this.icon = this.isCollapse ? "el-icon-s-unfold" : "el-icon-s-fold"
    }
  },
  created() {
    this.$router.push("/Home")
  }
}
</script>

<style scoped>
.el-header {
  border-bottom: #e3dddd 3px solid;
}

.el-main {
  padding: 5px;
}
</style>