<template>
  <div id="app">
    <router-view/>

  </div>
</template>

<script>

export default {
  name: 'App',
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{
    '$store.state.menu':{
      handler(val,old){
        if (!old && this.user &&this.user.userAccount){
          this.$store.commit("setRouter",val)
        }
      },
      immediate: true

    }
  }
}
</script>

<style>
#app {
  /*height: 100%;*/

}
</style>
