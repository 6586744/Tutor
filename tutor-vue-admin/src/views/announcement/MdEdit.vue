<template>
  <div class="home">
    <el-divider></el-divider>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="编辑公告"  />
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

      <el-form-item>
        <el-form-item label="是否可见">
          <el-switch v-model="form.delivery"></el-switch>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit('form')">提交</el-button><!--这个form配合prop以及ref用来做参数验证-->
        <el-button @click="backAllBlog()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  props : ['b_id'],
  name: "Md",
  components: {},
  data() {
    return {
      content: "",
      typeId:'',//初始化类型id
      b_typeList:[],//存放类型数组
      form : {
        b_name:'',
        b_type:'',
        b_typeItems : [], //遍历类型
        b_image:'',
        b_url: 'https://192.168.0.110:8083/imgUpload', //上传地址
        dynamicTags:[], //标签值
        inputVisible: false, //是否显示标签输入框
        inputValue: '',
        delivery:false //是否开启预览
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
    this.loading();
  },

  methods: {
    // 上传图片方法
    // $imgAdd(pos, $file) {
    //   console.log(pos, $file);
    // },
    //返回
    backAllBlog() {
      window.history.back();
    },

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
              '/blog/updateBlog',
              {
                b_id : this.$route.params.b_id,
                b_content: this.content,
                b_title: this.form.b_name,//数据库打错了
                b_show: b_show,
                b_image: this.form.b_image,
              }
            ).then(
              (resp) => {
                if (resp.data == "1") {
                  this.$confirm("修改成功！");
                  this.loading()
                } else {
                  this.$confirm("修改失败！");
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

    //加载页面数据
    loading() {
      console.log(this.$route.params.b_id);

      //获取资讯信息并填充页面数据
      this.axios.post(
        '/blog/queryBlog',
        {
            b_id : this.$route.params.b_id
        }
      ).then(
        (resp) => {
          this.content = resp.data[0].b_content;
          this.form.b_name = resp.data[0].b_title;
          this.form.b_image = resp.data[0].b_image
          if(resp.data[0].b_show == 1 || resp.data[0].b_show == "1") {
            this.form.delivery = true;
          }
          else {
            this.form.delivery = false;
          }
        }
      ).catch(
        (failResponse) => {
          this.$confirm("网络异常！");
        }
      )

    },


    //博客图片上传。并将文中的图片替换为远程地址
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
          //  替换文中的地址
          _this.$refs.md.$imglst2Url([[pos, imgUrl]])

        } else {
          _this.$message({type: json.status, message: json});
        }
      });
    },

    //封面上传
    handleAvatarSuccess(res, file) {
      console.log(res);
      console.log(file)
      this.form.b_image = this.axios.defaults.baseURL + '/' + res[0];
      console.log(this.form.b_image)
      this.form.b_url = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
    },

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
