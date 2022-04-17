<template>
  <body id="poster" >
    <div>
      <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
        <h3 class="login-title">欢迎注册</h3>
        <el-form-item label="身份">
          <el-radio v-model="form.radio" label="家长">家长</el-radio>
          <el-radio v-model="form.radio" label="教师">教师</el-radio>
        </el-form-item>
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
          <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="passwordTrue">
          <el-input type="password" placeholder="请确认密码" v-model="form.passwordTrue"/>
        </el-form-item>
        <el-form-item label="昵称" prop="u_show">
          <el-input type="text" placeholder="请输入昵称" v-model="form.u_show"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input type="text" placeholder="请输入邮箱" v-model="form.mail"/>
        </el-form-item>
        <el-form-item v-if="form.radio == '教师'" label="百度地图" prop="u_baidu_address">
          <el-input type="text" placeholder="请输入经纬度" v-model="form.u_baidu_address"/>
          <el-link href="http://api.map.baidu.com/lbsapi/getpoint/index.html" target="_blank" type="info">获取</el-link>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-select v-model="form.address" placeholder="请选择">
            <el-option
              v-for="(item, index) in addressList"
              :key="index"
              :label="item.a_name"
              :value="item.a_name">
            </el-option>
          </el-select>
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
import validator, {validateEMail, validatePhone} from '../utils/validator'

export default {
  name: "Register",
  data(){
    return {

      addressList:[],

      form:{
        sex: '男',
        radio: '家长',
        username: '',
        password: '',
        u_url: 'https://192.168.0.110:8083/imgUpload',
        u_image:'',
        passwordTrue:'',
        u_show:'',
        u_phone:'',
        mail:'',
        address:'',
        u_baidu_address: '',
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules:{
        username:[{required:true,message:'账号不能为空',trigger:'blur'}],
        u_image:{required: true, message: '请上传头像', trigger: 'change'},
        password:[{required: true,message: '密码不能为空',trigger:'blur'}],
        address:[{required: true,message: '地址不能为空',trigger:'blur'}],
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
        u_baidu_address:[{required:true,message:'地图经纬度不能为空',trigger:'blur'}],
        u_show:[{required:true,message:'昵称不能为空',trigger:'blur'}],
        u_phone:[{required: true,message: '电话不能为空',trigger:'blur'}, {validator : validatePhone, message:'电话输入不合法', trigger: 'blur'}],
        mail:[{required: true,message: '邮箱不能为空',trigger:'blur'}, {validator : validateEMail,message:'邮箱输入不合法', trigger: 'blur'}],
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
            let u_role = 0;
            if(this.form.radio == "家长") {
              //注册家长
              u_role = 1
            }
            else {
              u_role  = 2;
            }
            this.axios.post(
              '/user/addUser',
              {
                u_role: u_role,
                u_name :this.form.username,
                u_sex: this.form.sex,
                u_password:this.form.password,
                u_show:this.form.u_show,
                u_address_name: this.form.address,
                u_phone:this.form.u_phone,
                u_email:this.form.mail,
                u_image:this.form.u_image,
                u_baidu_address: this.form.u_baidu_address,
              }
            ).then(
              (info) => {
                console.log(info)
                if(info.data == "1") {
                  this.$confirm("注册成功！");
                  this.$router.push("/Login");
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
        }
      )
    },

    addTeacher() {
      this.axios.post(
        '/teacher/addTeacher',
        {
          t_name :this.form.username,
          t_sex: this.form.sex,
          t_password:this.form.password,
          u_show:this.form.u_show,
          t_address_name: this.form.address,
          t_phone:this.form.u_phone,
          t_email:this.form.mail,
          t_image:this.form.u_image
        }
      ).then(
        (info) => {
          console.log(info)
          if(info.data == "1") {
            this.$confirm("注册成功！");
            this.$router.push("/Login");
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
    },

    addUser() {
      this.axios.post(
        '/user/addUser',
        {
          u_name :this.form.username,
          u_sex: this.form.sex,
          u_password:this.form.password,
          u_show:this.form.u_show,
          u_address_name: this.form.address,
          u_phone:this.form.u_phone,
          u_mail:this.form.mail,
          u_image:this.form.u_image
        }
      ).then(
        (info) => {
          console.log(info)
          if(info.data == "1") {
            this.$confirm("注册成功！");
            this.$router.push("/Login");
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
    },

    loadAddressList() {
      this.axios.get(
        '/address/getAllAddress',
      ).then(
        (resp) => {
          // console.log(resp.data.length)
          this.addressList = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },

    handleClose() {

    }
  },

  mounted() {
    this.loadAddressList()
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
  overflow-y: auto;
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
  overflow-y: auto;
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
