<template>
  <div>
    <el-menu
      :default-active="'/index'"
      router
      mode="horizontal"
      background-color="white"
      text-color="#222"
      active-text-color="red">
      <!-- style="min-width: 1300px"> -->
<!--      <el-row style="margin-left: 50px">-->
      <el-menu-item  v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
<!--      </el-row>-->
<!--      <a href="#" @click="AdminExitSystem()" style="color: #222;float: right;padding: 20px;"><i class="el-icon-back"/>注销</a>-->
<!--      <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i>-->
      <el-dropdown style="float:right;;margin-right: 20px; margin-top: 7px"  @command="handleCommand">
        <el-row>
          <el-avatar style="float:right;;margin-right: 30px"
                     :src="imgSrc">
          </el-avatar>
        </el-row>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>{{u_show}}</el-dropdown-item>
          <el-dropdown-item divided @click.native="AdminExitSystem()"><i class="el-icon-back"/>注销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
<!--      <i style="float:right;font-size: 25px;color: #222;padding-top: 8px">{{u_show}}</i>-->
<!-- <span style="padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">家教系统</span> -->
    </el-menu>
    <router-view :key="$route.fullPath"/>
  </div>
</template>

<script>

import price from "./price";

export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [
        {name: '/indexOne', navItem: '首页'},
        {name: '/newOrder', navItem: '找学生'},
        {name: '/findTeacher',navItem: '找老师'},
        {name: '/BlogList', navItem: '公告中心'},
        // {name: '/price', navItem: '价格咨询'},
        {name: '/myIndex', navItem: '个人中心'}
      ],
      keywords: '',
      u_show : '',
      imgSrc : ''
    }
  },

  mounted() {
    this.u_show = this.$store.state.user.u_name.u_show;
    this.imgSrc = this.$store.state.user.u_name.u_image;
  },

  methods : {
    handleCommand() {
      // command;
    },

    search() {
      this.$router.push({
        name: 'SearchBlog',
        params: {search : this.keywords}
      })
    },

    AdminExitSystem() {
      this.$store.commit('exitSystem');
      this.$router.push('/Login')
    }
  }
}
</script>

<style scoped>
a{
  text-decoration: none;
}

span {
  pointer-events: none;
}
</style>

