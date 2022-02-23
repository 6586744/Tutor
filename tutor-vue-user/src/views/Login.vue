<template>
  <body id="poster" >
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="身份">
        <el-radio v-model="form.radio" label="家长">家长</el-radio>
        <el-radio v-model="form.radio" label="教师">教师</el-radio>
      </el-form-item>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>

        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
        <el-button type="danger" v-on:click="toRegister()">注册</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
  </body>
</template>

<script>
import store from "../store/modules/user";

export  default {
  name:"Login",
  data(){
    return {
      form:{
        radio: '家长',
        username: '',
        password: ''
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules:{
        username:[{required:true, message:'账号不能为空', trigger:'blur'}],
        password:[{required:true, message:'密码不能为空', trigger:'blur'}]
      },

      dialogVisible:false
    }
  },
  methods:{
    onSubmit(formName) {
      //为表单绑定验证功能
      this.$refs[formName].validate((valid) =>{
        if (valid){
          let role = 0;
          if(this.form.radio == "家长") {
            role = 1;
          }
          else {
            role = 2;
          }
          this.axios.post(
            '/user/userLogin',
            {
              u_phone: this.form.username,
              u_password: this.form.password,
              u_role: role,
              u_status: 1
            }
            //params
          ).then(
            (User) => {
              if(User.data != null && User.data != "") {
                //添加Vuex状态
                this.$store.commit('login', User.data)
                //跳转到视图
                this.$router.push("/");
                window.location.reload();
              }
              else {
                //提示框。
                this.$confirm("用户名或者密码错误！");
              }
            }
          ).catch(
            (failResponse) => {
              this.$confirm("网络异常！");
            }
          )
        }
      });
    },

    handleClose(done){
      this.$confirm('确定关闭吗').then(() => {
        // function(done)，done 用于关闭 Dialog
        done();

        console.info("点击右上角 'X' ，取消按钮或遮罩层时触发");
      }).catch(() => {

        console.log("点击确定时触发");
      });
    },

    toRegister() {
      this.$router.push("/Register")
    }
  },

  //路由验证
  beforeRouteEnter:(to, from, next) => {
    //验证是否登录
    if (store.state.user.u_name) {
      console.log("store.state.user : " + store.state.user.u_name)
      //路由跳转
      next({
        path : '/userHome'
      })
      window.location.reload();
    } else {
      next()
    }
  }
}
</script>

<style lang="scss" scoped>
.login-box{
  border: 1px solid #DCDFE6;
  width: 350px;
  margin:180px auto;
  padding:35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow:0 0 25px #909399;
}

.login-title{
  text-align:center;
  margin:0 auto 40px auto;
  color:#303133;
}

#poster {
  // background:url("../assets/b.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0px;
}

</style>
