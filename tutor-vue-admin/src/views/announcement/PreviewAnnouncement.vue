<template>
  <div>
  <el-divider></el-divider>
  <div style=" margin-right: 300px; margin-left: 300px">
    <div class="article clearfix">
      <el-button @click="backHistory()" style="float: right" icon="el-icon-back">返回</el-button>
      <div style="weight : 75%"class="article-left fl">
        <div class="header">
          <h1 style="margin: 20px 0 0;text-align: center; font-size: 34px;font-weight: bold;">{{Blog.b_title}}</h1>
          <div style="position: relative;
      margin: 30px 0 40px;
      padding-left: 50px;">
            <div class="zouZheInfo">
              <span class="zuoZheName">
                <span>{{Blog.b_user_name}}</span>
              </span>
              <div props-data-classes="user-follow-button-header"
                   data-author-follow-button="" />
              <div class="blogHot">
                <span class="publish-time">
                  {{Blog.time}}
                </span>
<!--                <span class="wordage">-->
<!--                  字数 {{Blog.b_content.length}}-->
<!--                </span>-->
<!--                <span class="views-count">-->
<!--                  阅读 {{Blog.b_hot}}-->
<!--                </span>-->
<!--                <span class="comments-count">-->
<!--                  评论 {{Blog.b_comment}}-->
<!--                </span>-->
<!--                <span class="likes-count">-->
<!--                  喜欢 {{Blog.b_start}}-->
<!--                </span>-->
<!--                <span class="likes-count">-->
<!--                  收藏 {{Blog.b_collection}}-->
<!--                </span>-->
              </div>
            </div>
<!--            <div class="tags "-->
<!--                 title="标签">-->
<!--              <el-tag size="mini"-->
<!--                      v-for="tag in tags"-->
<!--                      :key="tag.t_id"-->
<!--                      class="tag"-->
<!--                      type="success">{{tag.t_name}}</el-tag>-->
<!--            </div>-->
            <span class="clearfix" />
          </div>
        </div>

        <div class="content">
          <mavon-editor
            class="md"
            :value="Blog.b_content"
            :subfield = "mdContent.subfield"
            :defaultOpen = "mdContent.defaultOpen"
            :toolbarsFlag = "mdContent.toolbarsFlag"
            :editable="mdContent.editable"
            :scrollStyle="mdContent.scrollStyle"
          />
<!--          </mavon-editor>-->
        </div>

    </div>

    </div>

  </div>
  </div>
</template>
<script>

export default{
  name:'PreviewBlog',
  props : ['b_id'],
  data() {
    return {
      canCollection:true,
      start:'0',
      commentNum: 3,
      comment : '',
      userCanCollection : [],
      Blog:{
        b_id:'',
        b_user_id:'',
        b_user_name:'',
        b_title:'',
        b_image:'',
        b_content:'',
        b_show:'',
        time:'',
      },
      tags:[],
      mdContent:{
        subfield: false,// 单双栏模式
        defaultOpen: 'preview',//edit： 默认展示编辑区域 ， preview： 默认展示预览区域
        editable: false,
        toolbarsFlag: false,
        scrollStyle: true
      },
      allComment:[],
      // 默认显示第几页
      currentPage:1,
      // 个数选择器（可修改）
      pageSizes:[1,2,3,4,5,6,7,8,9,10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:10,
    }
  },

  mounted() {
    this.loadBlog();
  },

  methods : {
    //返回
    backHistory() {
      window.history.back();
    },


    //加载博客内容
    loadBlog() {
      this.axios.post(
        '/blog/queryBlog',
        {
            b_id : this.$route.params.b_id
        }
      ).then(
        (resp) => {
          console.log(resp.data)
          this.Blog = resp.data[0]
        }
      ).catch(
        (failResponse) => {
          this.$confirm("网络异常！");
        }
      )
    },
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    handleCurrentChange(val) {
      this.currentPage=val
    },
  }

}
</script>

<style scoped>
/*<!--头部div -->*/
.heart {
  height: 60px;
  text-align: center;
  margin: 50px;
}
/*作者那个样式*/
.zuozheavatar {
  position: absolute;
  left: 0;
  top: 0;
  width: 48px;
  height: 48px;
  vertical-align: middle;
  display: inline-block;
}
.imgTouxiang {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
.zouZheInfo{
  float: left;
  vertical-align: middle;
/*// display: inline-block;*/
  margin-left: 8px;
}
.zuoZheName {
  margin-right: 3px;
  font-size: 16px;
  vertical-align: middle;
}
.blogHot {
  margin-top: 5px;
  font-size: 12px;
  color: #969696;
}
span {
  padding-right: 5px;
}
.tags {
  float: right;
  padding-top: 15px;
/*// padding-right: 20px;*/
}
.tag {
/*// padding: 0 10px;*/
  margin-left: 5px;
  border-right: 2px solid #eee;
}
/*内容*/
.content {
  min-height: 300px;
}


/*评论*/
.comment-list {
  margin-top: 30px;
}
.top-title {
  padding-bottom: 20px;
  font-size: 17px;
  font-weight: 700;
  border-bottom: 1px solid #f0f0f0;
}
.item {
  padding: 20px 0 30px;
  border-bottom: 1px solid #f0f0f0;
}
.item-header {
  position: relative;
  padding-left: 45px;
  padding-bottom: 10px;
}
.author {
  position: absolute;
  left: 0;
  display: inline-block;
}
.avatar {
  display: inline-block;
  margin-right: 5px;
  width: 40px;
  height: 40px;
  vertical-align: middle;
}
.img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
.info {
  display: inline-block;
}
.name {
  font-size: 15px;
  color: #333;
}
.time {
  font-size: 12px;
  color: #969696;
}
.comment-detail {
  min-height: 40px;
}
.like {
  margin-right: 20px;
}
.item-other {
  margin: 20px;
  padding: 10px;
  border-left: 2px solid #f0f0f0;
}
.item-other-header {
  position: relative;
  padding-left: 45px;
  padding-bottom: 10px;
}
.item-other-author {
  position: absolute;
  left: 0;
  display: inline-block;
}
.item-other-avatar {
  display: inline-block;
  margin-right: 5px;
  width: 38px;
  height: 38px;
  vertical-align: middle;
}
.item-other-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
.item-other-info {
  display: inline-block;
}
.item-other-name {
  font-size: 15px;
  color: #333;
}
.item-other-time {
  font-size: 12px;
  color: #969696;
}
.item-other-comment-detail {
  min-height: 40px;
  border-bottom: 1px dashed #f0f0f0;
}
.message {
  padding: 10px;
}
</style>

