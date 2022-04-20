<template>
  <div class="home">
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="添加公告"  />
    <el-divider></el-divider>
    <mavon-editor
      ref="md"
      placeholder="请输入文档内容..."
      :boxShadow="false"
      style="z-index:1;border: 1px solid #d9d9d9;height:50vh"
      v-model="content"
      :toolbars="toolbars"
      @imgAdd="imgAdd"
    />
    <el-divider></el-divider>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" ><!--ref="form"-->
      <el-form-item label="公告标题" prop="b_name">
        <el-input v-model="form.b_name"></el-input>
      </el-form-item>
      <el-form-item label="公告封面" prop="b_image">
        <el-upload
          class="avatar-uploader"
          :show-file-list="false"
          :action="form.b_url"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="form.b_image" :src="form.b_image" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="是否可见">
          <el-switch v-model="form.delivery"></el-switch>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('form')">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Md",
  components: {},
  data() {
    return {
      content: "",
      b_typeList:[],//存放类型数组
      form : {
        b_name:'',
        b_image:'',
        b_url: 'https://192.168.0.110:8083/imgUpload', //上传地址
        m_url:'https://192.168.0.110:8083/imgUpload',
        delivery:false, //是否开启预览
      },
      rules: {
        b_name: [
          {required: true, message: '请输入资讯标题', trigger: 'blur'},
          {min: 3, max: 25, message: '长度在 3 到 25 个字符', trigger: 'blur'},
        ],
        b_type:{required: true, message: '请输入资讯类型', trigger: 'change'},
        b_image:{required: true, message: '请上传封面', trigger: 'change'},
      },
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true // 预览
      }
    };
  },

  mounted() {
  },

  methods: {

    submit(form) {
      if (this.content == '') {
        this.$confirm("请输入点什么吧！")
      } else {
        this.$refs[form].validate((valid) => {
          if(valid) {
            console.log("valid" + valid)
            let b_show = 0
            if (this.form.delivery == true) {
              b_show = 1;
            }
            this.axios.post(
              '/blog/addBlog',
              {
                b_content: this.content,
                b_user_id: this.$store.state.user.userInfo.user.a_id,
                b_user_name: this.$store.state.user.userInfo.user.a_name,
                b_title: this.form.b_name,//数据库打错了
                b_show: b_show,
                b_image: this.form.b_image,
              }
            ).then(
              (resp) => {
                if (resp.data == "1") {
                  this.$confirm("上传成功！");
                  this.content = '';
                  this.form.b_name = '';
                  this.form.b_image = '';
                  this.form.dynamicTags = [];
                  this.form.delivery = false;
                } else {
                  this.$confirm("上传失败！");
                }
              }
            ).catch(
              (failResponse) => {
                this.$confirm("网络异常！");
              }
            )
          }
        })
      }

    },

    //图片上传。并将文中的图片替换为远程地址
    imgAdd(pos, $file){
      let _this = this;
      // 第一步.将图片上传到服务器.
      let file = new FormData();
      file.append('file', $file);
      this.axios({
        method: 'post',
        url: `/imgUpload`,
        data: file,
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(resp=> {
        let json = resp.data[0]; //取出上传成功后的url
        console.log(json)
        let imgUrl = this.axios.defaults.baseURL + '/' +json
        // console.log(resp)
        if (resp.status == 200) {
          //  _this.$refs.md.$imgUpdateByUrl(pos, json.msg)
          _this.$refs.md.$imglst2Url([[pos, imgUrl]])

        } else {
          _this.$message({type: json.status, message: json});
        }
      });
    },

    //封面上传
    handleAvatarSuccess(res, file) {
      this.form.b_image = this.axios.defaults.baseURL + '/' + res[0];
      this.form.b_url = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
    },

    handleInputConfirm() {
      let inputValue = this.form.inputValue;
      if (inputValue) {
        this.form.dynamicTags.push(inputValue);
      }
      this.form.inputVisible = false;
      this.form.inputValue = '';
    }
  }

};
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
