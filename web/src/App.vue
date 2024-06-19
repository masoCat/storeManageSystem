<template>
  <div id="app">
    <HomePage></HomePage>
  </div>
</template>

<script>

import HomePage from "@/components/HomePage";

export default {
  name: 'App',
  components: {
    HomePage
  }
}
</script>

<style>
#app {
  height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
