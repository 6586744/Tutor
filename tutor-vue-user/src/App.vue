<template>
  <div id="app">
<!--    展示显示的内容-->
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
}
</script>
<style>
#app {
  /*background:url("assets/e.jpg") no-repeat;*/
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  overflow-y: auto;
}
</style>

