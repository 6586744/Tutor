<template>
  <div style=" width: 70%; margin: 25px auto">
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="头像" prop="u_image">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          :action="u_url"
          v-if="editTure == false" disabled
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="form.u_image" :src="form.u_image" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          :action="u_url"
          v-else
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="form.u_image" :src="form.u_image" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="姓名" prop="u_name">
        <el-input v-if="editTure == false" :disabled="true" v-model="form.u_name"></el-input>
        <el-input v-else :disabled="true" v-model="form.u_name"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="u_show">
        <el-input v-if="editTure == false" :disabled="true" v-model="form.u_show"></el-input>
        <el-input v-else v-model="form.u_show"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="u_email">
        <el-input v-if="editTure == false" :disabled="true" v-model="form.u_email"></el-input>
        <el-input v-else v-model="form.u_email"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="u_phone">
        <el-input v-if="editTure == false" :disabled="true" v-model="form.u_phone"></el-input>
        <el-input v-else :disabled="true" v-model="form.u_phone"></el-input>
      </el-form-item>
      <el-form-item v-if="u_role == '2'" label="备注" prop="u_remake">
        <el-input v-if="editTure == false" :disabled="true" v-model="form.u_remake"></el-input>
        <el-input v-else v-model="form.u_remake"></el-input>
      </el-form-item>
<!--      <el-form-item label="创建时间">-->
<!--        <span>{{form.time}}</span>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button v-if="editTure == false" type="primary" @click="onSubmit">修改</el-button>
        <el-button v-else plain disabled type="primary" @click="onSubmit">修改</el-button>

        <el-button v-if="editTure" type="primary" @click="editUserInfo('form')">保存</el-button>
        <el-button v-else plain disabled type="primary" @click="editUserInfo('form')">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import validator, {validateEMail, validatePhone} from '../../utils/validator'

export default {
  name: "UserInfo",
  data() {
    return {
      editTure : false,
      u_role:'',
      u_url: 'https://192.168.0.110:8083/imgUpload',//头像上传地址
      form : {
        u_id :'',
        u_name:'',
        u_password:'',
        u_show:'',
        u_image:'',
        u_phone:'',
        u_email:'',
        time:'',
        u_remake: '',

      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules:{
        u_name:[{required:true,message:'账号不能为空',trigger:'blur'}],
        u_image:{required: true, message: '请上传头像', trigger: 'change'},
        u_password:[{required: true,message: '密码不能为空',trigger:'blur'}],
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
        u_show:[{required:true,message:'昵称不能为空',trigger:'blur'}],
        u_phone:[{required: true,message: '电话不能为空',trigger:'blur'}, {validator : validatePhone, message:'电话输入不合法', trigger: 'blur'}],
        u_email:[{required: true,message: '邮箱不能为空',trigger:'blur'}, {validator : validateEMail,message:'邮箱输入不合法', trigger: 'blur'}],
      },
    }
  },

  mounted() {
    this.loadUser();
    this.u_role = this.$store.state.user.u_name.u_role;
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
            this.axios.post(
              '/user/updateUser',
              {
                u_name : this.form.u_name,
                u_phone :this.form.u_phone,
                u_email : this.form.u_email,
                u_show : this.form.u_show,
                u_id : this.$store.state.user.u_name.u_id,
                u_image: this.form.u_image,
                u_remake: this.form.u_remake
              }
            ).then(
              (resp) => {
                console.log(resp.data)
                if (resp.data == 1) {
                  this.$confirm("修改成功！");
                  this.loadUser();
                  this.editTure = false;
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

    //封面上传
    handleAvatarSuccess(res, file) {
      this.form.u_image = this.axios.defaults.baseURL + '/' + res[0];
    },
    beforeAvatarUpload(file) {
    },

    loadUser() {
      this.axios.post(
        '/user/queryUser',
        {
          u_id : this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          console.log(resp.data)
          this.form.u_password = resp.data[0].u_password
          this.form.u_phone = resp.data[0].u_phone
          this.form.u_image = resp.data[0].u_image
          this.form.u_show = resp.data[0].u_show
          this.form.u_email = resp.data[0].u_email
          this.form.u_name = resp.data[0].u_name
          this.form.time = resp.data[0].time
          this.form.u_remake = resp.data[0].u_remake
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
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
