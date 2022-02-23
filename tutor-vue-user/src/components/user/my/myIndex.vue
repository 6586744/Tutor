<template>
  <div class="index">
    <el-container style="height: 100%" direction="vertical">
      <!--      <el-header style="height: 100px">-->
      <!--        <div style="float: left">-->
      <!--          <span style="font-size: 22px;">后台管理系统</span>-->
      <!--        </div>-->
      <!--        <div style="float: right">-->
      <!--          <div class="demo-basic&#45;&#45;circle">-->
      <!--            <div class="block" >-->
      <!--              <el-dropdown >-->
      <!--                <el-avatar size="large" :src="imgSrc"></el-avatar>-->
      <!--                &lt;!&ndash;                <i class="el-icon-setting"></i>&ndash;&gt;-->
      <!--                <el-dropdown-menu slot="dropdown">-->
      <!--                  <el-dropdown-item @click.native="AdminExitSystem()">注销</el-dropdown-item>-->
      <!--                </el-dropdown-menu>-->
      <!--              </el-dropdown>-->
      <!--              <span>{{u_show}}</span>-->
      <!--            </div>-->
      <!--          </div>-->
      <!--        </div>-->
      <!--      </el-header>-->
      <el-container class="footer">
        <el-aside width="200px">
          <el-menu class="el-menu"
            ><!--:default-openeds="['1']"-->
            <el-submenu index="1" class="el-menu">
              <template slot="title" class="el-border"
                ><i class="el-icon-user"></i>个人信息</template
              >
              <el-menu-item-group class="el-group">
                <el-menu-item index="1-1">
                  <router-link to="/UserInfo"
                    ><i class="el-icon-s-custom"></i>基本信息</router-link
                  >
                </el-menu-item>
                <el-menu-item index="1-2">
                  <router-link to="/editorPassword"
                    ><i class="el-icon-s-tools"></i>修改密码</router-link
                  >
                </el-menu-item>
                
                <el-menu-item v-if="role == '2'" index="1-3">
                  <router-link to="/education"
                    ><i class="el-icon-collection"></i>教育经历</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '2'" index="1-4">
                  <router-link to="/experience"
                    ><i class="el-icon-collection"></i>教学经验</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '2'" index="1-5">
                  <router-link to="/expert"
                    ><i class="el-icon-collection"></i>擅长科目</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '2'" index="1-5">
                  <router-link to="/previewUserInfo"
                    ><i class="el-icon-collection"></i>信息预览</router-link
                  >
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-submenu index="2">
              <template slot="title" class="el-border"
                ><i class="el-icon-menu"></i>订单管理</template
              >
              <el-menu-item-group class="el-group">
                <el-menu-item v-if="role == '1'" index="2-1">
                  <router-link to="/userOrder"
                    ><i class="el-icon-edit"></i>我的订单</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '1'" index="2-2">
                  <router-link to="/userAddOrder"
                    ><i class="el-icon-s-tools"></i>添加订单</router-link
                  >
                </el-menu-item>

                <el-menu-item v-if="role == '2'" index="2-1">
                  <router-link to="/teacherOrder"
                    ><i class="el-icon-edit"></i>我的订单</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '2'" index="2-2">
                  <router-link to="/teacherSubscribe"
                    ><i class="el-icon-edit"></i>我的预约</router-link
                  >
                </el-menu-item>
                <el-menu-item v-if="role == '2'" index="2-3">
                  <router-link to="/teacherCheckSubscribe"
                    ><i class="el-icon-edit"></i>预约我的</router-link
                  >
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main>
            <router-view />
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "myIndex",
  data() {
    return {
      refreshIndex: 1,
      u_show: "",
      imgSrc: "",
      role: "",
    };
  },

  // 元素挂载结束，可以获取dom 元素
  mounted() {
    this.role = this.$store.state.user.u_name.u_role;
    console.log("?" + this.role);
    console.log(this.$store.state.user.u_name.u_id);
  },

  methods: {},
};
</script>

<style scoped>
.el-header {
  background-color: #ffffff;
  color: #000000;
  text-align: center;
  line-height: 100px;
}
.el-footer {
  background-color: #ededed;
  color: #000000;
  text-align: center;
  line-height: 100px;
}
.el-aside {
  background-color: #ffffff;
  color: #000000;
  text-align: center;
  line-height: 200px;
}

.el-menu {
  background-color: #ffffff;
  color: #000000;
}
/*.el-border {*/
/*  border-style: solid;*/
/*  border-width: 1px;*/
/*  border-color: #303133;*/
/*}*/
.el-group {
  background-color: #eceaea;
  color: #000000;
}
/*设置容器布局填充屏幕*/
.index {
  height: calc(100vh);
}
html,
body,
#app,
.el-container {
  /*统一设置高度为100%*/
  height: 100%;
}

/*去掉routerlink自带的下划线与蓝色样式*/
a {
  text-decoration: none;
  color: #000000;
}
.router-link-active {
  text-decoration: none;
  color: #323232;
}
/**/
</style>
