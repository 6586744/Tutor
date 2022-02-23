<template>
  <div style=" width: 70%; margin: 25px auto">
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="原始密码" >
        <el-input type="password" v-model="form.oldPassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input  type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="truePassword">
        <el-input  type="password" v-model="form.truePassword"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button plain  type="primary" @click="editUserInfo('form')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import validator, {validateEMail, validatePhone} from '../../../utils/validator'

export default {
  name: "UserInfo",
  data() {
    return {

      form : {
        oldPassword:'',
        editPassword:'',
        password:'',
        truePassword:'',
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules:{
        password:[{required: true,message: '密码不能为空',trigger:'blur'}],
        truePassword:[{required: true,message: '密码不能为空',trigger:'blur'} , {
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
      },
    }
  },

  mounted() {
    this.loadUser()
  },

  methods: {
    onSubmit() {
      this.editTure = true
    },

    //修改个人信息
    editUserInfo(form) {
      this.$refs[form].validate(
        (valid) => {
          if(valid) {
            if(this.form.oldPassword != this.form.editPassword) {
              this.$confirm("旧密码输入错误");
              return ;
            }
            if(this.form.oldPassword == this.form.password) {
              this.$confirm("新旧密码不能一样");
              return ;
            }
            this.axios.post(
              '/user/updateUser',
              {
                u_password: this.form.password,
                u_id : this.$store.state.user.u_name.u_id,
              }
            ).then(
              (resp) => {
                console.log(resp.data)
                if (resp.data == 1) {
                  this.$confirm("修改成功！");
                  this.loadUser();
                  this.editTure = false;
                  this.form.editPassword = this.form.password
                }
                else {
                  this.$confirm("修改失败！");
                }
              }
            ).catch(
              (resp) => {
                this.$confirm("网络异常！");
              }
            )
          }
        }
      )
    },

    loadUser() {
      this.form.editPassword = this.$store.state.user.u_name.u_password;
    }
  },


}

</script>

<style scoped>
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
