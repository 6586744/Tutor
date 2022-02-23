<template>
  <body id="poster" >
    <div>
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
        <h3 class="login-title">欢迎注册</h3>
        <el-form-item label="头像" prop="u_image">
          <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :action="form.u_url"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="form.u_image" :src="form.u_image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="电话" prop="u_phone">
          <el-input type="text" placeholder="请输入电话" v-model="form.u_phone"/>
        </el-form-item>
        <el-form-item label="姓名" prop="username">
          <el-input type="text" placeholder="请输入真实姓名" v-model="form.username"/>
        </el-form-item>
        <el-form-item label="昵称" prop="u_show">
          <el-input type="text" placeholder="请输入昵称" v-model="form.u_show"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="passwordTrue">
          <el-input type="password" placeholder="请确认密码" v-model="form.passwordTrue"/>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input type="text" placeholder="请输入邮箱" v-model="form.u_email"/>
        </el-form-item>

        <el-form-item label="住址" prop="address">
          <el-input type="text" placeholder="请输入地址" v-model="form.address"/>
        </el-form-item>
        <el-form-item>
          <!-- <el-button type="primary" v-on:click="onSubmit()">登录</el-button> -->
          <el-button type="danger" v-on:click="toRegister('loginForm')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </body>
</template>

<script>
import validator, {validateEMail, validatePhone} from '../../utils/validator'

export default {
  name: "Register",
  data(){
    return {
      form:{
        username: '',//真实姓名
        password: '',//确认密码
        u_url: 'http://127.0.0.1:8083/imgUpload',
        u_image:'',//头像
        passwordTrue:'',
        u_email:'',//邮箱
        u_phone:'',//电话号码
        u_show:'',//昵称
        address:'',//地址
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules:{
        username:[{required:true,message:'真实姓名不能为空',trigger:'blur'}],
        u_image:{required: true, message: '请上传头像', trigger: 'change'},
        u_show:{required: true, message: '请输入昵称', trigger: 'change'},
        password:[{required: true,message: '密码不能为空',trigger:'blur'}],

        passwordTrue:[{required: true,message: '密码不能为空',trigger:'blur'} , {
            validator:(rule,value,callback)=>{
              if(value===''){
                callback(new Error('请再次输入密码'))
              }else if(value!==this.form.password){
                callback(new Error('两次输入密码不一致'))
              }else{
                callback( )
              }
            },
            trigger:'blur'
          }
        ],
        u_phone:[{required: true,message: '电话不能为空',trigger:'blur'}, {validator : validatePhone, message:'电话输入不合法', trigger: 'blur'}],
        address:[{required: true,message: '住址不能为空',trigger:'blur'}],
      },
      //对话框显示和隐藏
      dialogVisible:false
    }
  },
  methods : {
    onSubmit() {
      this.$router.push("/")
    },

    //封面上传
    handleAvatarSuccess(res, file) {
      console.log(res);
      console.log(file)
      this.form.u_image = this.axios.defaults.baseURL + '/' + res[0];
      console.log(this.form.u_image)
      console.log("url" + this.form.u_url)
    },
    beforeAvatarUpload(file) {
    },

    toRegister(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.axios.post(
              '/user/userRegister',
              {
                  user_show: this.form.u_show,
                  user_name: this.form.username,
                  user_password: this.form.password,
                  user_phone: this.form.u_phone,
                  user_email:this.form.u_email,
                  user_image: this.form.u_image,
                  user_address: this.form.address
              }
          ).then(
            (info) => {
              console.log(info)
              if(info.data == "1") {
                this.$confirm("注册成功！");
                // this.$router.push("/login");
              }
              else if(info.data == '2'){
                this.$confirm("该电话已被注册！");
              }
              else {
                this.$confirm(info.data);
              }
            }
          ).catch(  //请求成功 确进入这个方法，说明，then方法有问题
            (failResponse) => {
              this.$confirm("网络异常！");
            }
          )
        }
        else {
          return false;
        }
      }
    )},

    handleClose() {

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
  background-color: #20a0ff;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
