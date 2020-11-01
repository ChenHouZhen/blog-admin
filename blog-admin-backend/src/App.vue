<template>
  <div id="app">
    <router-view/>
    <!-- <footers></footers> -->
  </div>
</template>

<script>
import Footers from '@/components/Footers'

export default {
  name: 'App',
  components:{
    Footers
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

* {
    padding: 0;
    margin: 0;
}
</style>
