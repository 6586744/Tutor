<template>
  <div style="width: 70%; margin: 0 auto">
    <div class="comment-list">
      <div class="top-title">
        <span>{{blogList.length}} 条博客</span>
      </div>
      <div
        v-for="(item, i) in blogList"
        :key="i"
        class="item"
      >
        <!--        <div style="display:inline;width: 75%; height: 200px;display:inline-block; float:left;">-->
        <div class="item-header">
          <div class="author">
            <div class="demo-basic--circle">
              <div class="block"><el-avatar size="medium" :src="item.b_userimage"></el-avatar></div>
              <!--                </div>-->
            </div>
          </div>
          <div class="info" style="width: 80%; height: 120px;float: left" >
            <div class="name">
              {{item.b_username}}
            </div>
            <div class="time">
              {{item.b_createtime}}
            </div>
          </div>
          <img style=" width: 20%;;height: 140px;float: right;" :src="item.b_image"/>
        </div>


        <div class="comment-detail" >
          <a @click="toPreviewBlog(item.b_id)">{{item.b_titile}}</a>
          <el-button type="danger"
                     size="large"
                     icon="heart"
                     @click="deleteBlog(item.b_id)">
            删除
          </el-button>
        </div>

      </div>
      <!--        <div style="width: 25%; height: 200px;display:inline;float:left;">-->
      <!--          <img style="width:300px; height: 90%" src="../../assets/background.jpg"/>-->
      <!--        </div>-->
      <!--      </div>-->

    </div>

  </div>
</template>

<script>
export default {
  name: "MyCollection",
  data() {
    return {
      blogList:[],
    }
  },

  mounted() {
    this.loadBlog();
  },

  methods: {

    //删除收藏deleteCollection
    deleteBlog(b_id) {
      this.axios.post(
        '/collection/deleteCollection',
        {
          c_userid: this.$store.state.user.u_name.u_id,
          c_blogid: b_id
        }
      ).then(
        (resp) => {
          this.$confirm("删除成功");
          this.loadBlog()
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },

    //博客详情页面
    toPreviewBlog(id) {
      this.$router.push(
        {
          name: 'userBlog',
          params: {
            b_id : id
          }
        }
      )
    },

    //加载博客
    loadBlog() {
      this.axios.get(
        '/collection/getBlogCollectionByUserId',
        {
          params : {id : this.$store.state.user.u_name.u_id}
        }
      ).then(
        (resp) => {
          this.blogList = resp.data
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    }
  }
}
</script>

<style scoped>
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
  padding: 10px 0 10px;
  /*//底部边宽*/
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
  font-size: 25px;
  font-style: italic;
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
