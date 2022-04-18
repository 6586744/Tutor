<template>
  <div id="poster">
    <div style="width: 70%; margin: 0 auto">

      <div class="comment-list">
        <el-alert title="最新公告" type="success" :close-text="blogList.length">
        </el-alert>
        <el-divider></el-divider>
        <div v-for="(item, i) in blogList" :key="i" class="item">
          <div class="item-header">
            <div class="info" style="width: 80%; float: left">
              <div class="name">
                {{ item.b_user_name }}
              </div>
              <div class="time">
                {{ item.time }}
              </div>
            </div>
            <img style="width: 20%; float: right" :src="item.b_image" />
          </div>
          <div class="comment-detail">
            <a @click="toPreviewBlog(item.b_id)">{{ item.b_title }}</a>
          </div>
        </div>
      </div>

      <div class="comment-list">
        <el-alert
          title="最新订单"
          type="success"
          :close-text="orderList.length"
        >
        </el-alert>
        <el-divider></el-divider>
        <div v-for="(item, i) in orderList" :key="i" class="item">
          <table>
            <tr>
              <td>
                <div style="margin-left: 10px" class="item-header">
                  <div class="author">
                    <div class="demo-basic--circle">
                      <div class="block">
                        <el-avatar
                          size="medium"
                          :src="item.o_student_image"
                        ></el-avatar>
                      </div>
                    </div>
                  </div>
                  <div
                    class="info"
                    style="width: 80%; height: 120px; float: left"
                  >
                    <div class="name">
                      {{ item.o_student_name }}
                    </div>
                    <div class="time">
                      {{ item.time }}
                    </div>
                  </div>
                </div>
              </td>
              <td>
                <div @click="toOrderInfo(item.o_id)" class="comment-detail">
                  <p>孩子姓名：{{ item.o_name }}</p>
                           <p>教学科目：{{item.o_type}}</p>
                  <!--          <p>教学科目：{{item.o_type}}</p>-->
                  <p>教学地址：{{ item.o_address }}</p>
                  <p>要求：{{ item.o_time }}----{{ item.o_reamke }}</p>
                  <!--          <a @click="toPreviewBlog(item.b_id)">{{item.b_titile}}</a>-->
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>
      <el-dialog title="详情" :visible.sync="showOrderInfo">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>订单详情</span>
            </div>
            <div class="name-role">
              <span class="sender">{{ user.o_student_name }}</span>
            </div>
            <div class="name-role">
              <el-avatar style="text-align: center" :size="60">
                <img :src="user.o_student_image" />
              </el-avatar>
            </div>
            <div class="registe-info">
              <span class="registe-info">
                发布时间：
                <li class="fa fa-clock-o"></li>
                {{ user.time }}
              </span>
            </div>
            <el-divider></el-divider>
            <div class="personal-relation">
              <div class="relation-item">
                学生姓名:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_name }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                联系方式:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_phone }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                学生性别:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_sex }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                学生年龄
                <div style="float: right; padding-right: 20px">
                  {{ user.o_age }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                科目:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_type }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                价格:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_price }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                上课时间:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_time }}
                </div>
              </div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">
                备注:
                <div style="float: right; padding-right: 20px">
                  {{ user.o_reamke }}
                </div>
              </div>
            </div>
          </el-card>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="showOrderInfo = false">取 消</el-button>
          <el-button type="primary" @click="addTeacherSubscribe()"
            >预 约</el-button
          >
        </div>
      </el-dialog>

      <div class="comment-list">
        <el-alert
          title="老师推荐"
          type="success"
          :close-text="teacherList.length"
        >
        </el-alert>
        <el-divider></el-divider>
        <div v-for="(item, i) in teacherList" :key="i" class="item">
          <table>
            <tr>
              <td>
                <div style="margin-left: 10px" class="item-header">
                  <div class="author">
                    <div class="demo-basic--circle">
                      <div class="block">
                        <el-avatar
                          size="medium"
                          :src="item.u_image"
                        ></el-avatar>
                      </div>
                      <!--                </div>-->
                    </div>
                  </div>
                  <div
                    class="info"
                    style="width: 80%; height: 120px; float: left"
                  >
                    <div class="name">
                      {{ item.u_name }}
                    </div>
                    <div class="time">
                      {{ item.time }}
                    </div>
                  </div>
                </div>
              </td>
              <td>
                <div @click="toTeacherInfo(item.u_id)" class="comment-detail">
                  <p>性别：{{ item.u_sex }}</p>
                  <!--          <p>教学科目：{{item.o_type}}</p>-->
                  <!--          <p>教学科目：{{item.o_type}}</p>-->
                  <p>地址：{{ item.u_address_name }}</p>
                  <p>备注：{{ item.u_remake }}</p>
                  <!--          <a @click="toPreviewBlog(item.b_id)">{{item.b_titile}}</a>-->
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>
      <!-- <el-alert
          style="margin-top: 20px; margin-bottom: 20px"
          title="到底了"
          type="success"
        >
        </el-alert> -->
    </div>
    <!--      <div style="width: 15%; height:50px;float: right;  display: inline-block">-->
    <!--        <el-calendar class="cale"v-model="value">-->
    <!--        </el-calendar>-->
    <!--      </div>-->
    <!--    </el-row>-->
  </div>
</template>

<script>
export default {
  name: "BlogList",
  data() {
    return {
      imgSrc: require("../../assets/q.jpeg"),
      imageList: [
        { image: require("../../assets/b.jpg"), url: "www.baidu.com" },
        { image: require("../../assets/e.jpg") },
        { image: require("../../assets/w.jpg") },
        { image: require("../../assets/q.jpeg") },
      ],

      value: new Date(),

      blogList: [],

      priceList: [],

      /*
      订单相关
       */
      userRole: "",

      orderList: [],
      // 默认显示第几页
      currentPage: 1,
      // 个数选择器（可修改）
      pageSizes: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount: 1,
      // 个数选择器（可修改）
      PageSize: 10,
      activeName: "second",

      /**
       * 订单详情相关
       */
      showOrderInfo: false,
      user: {
        o_id: "",
        o_student_id: "",
        o_student_name: "",
        o_student_image: "",
        o_name: "",
        o_phone: "",
        o_price: "",
        o_type: "",
        o_sex: "",
        o_age: "",
        o_reamke: "",
        o_address: "",
        o_time: "",
      },

      teacherList: [],
    };
  },

  mounted() {
    this.loadBlog();
    this.loadOrder();
    this.loadTeacher();
  },

  methods: {
    outUrl(url) {
      window.location.href = url;
    },

    loadPrice() {
      this.axios
        .get("/price/getAllPrice")
        .then((resp) => {
          this.priceList = resp.data;
        })
        .catch((resp) => {
          // this.$confirm("网络异常！");
        });
    },

    //博客详情页面
    toPreviewBlog(id) {
      this.$router.push({
        name: "myBlog",
        params: {
          b_id: id,
        },
      });
    },

    //加载博客
    loadBlog() {
      this.axios
        .post("/blog/queryBlog", {
          b_show: "1",
        })
        .then((resp) => {
          if (resp.data.length >= 10) {
            this.blogList = resp.data.slice(0, 10);
          } else {
            this.blogList = resp.data;
          }
        })
        .catch((resp) => {
          // this.$confirm("网络异常！");
        });
    },

    /**
     * 教师预约学生
     */
    addTeacherSubscribe() {
      if (this.$store.state.user.u_name.u_teachering == "1") {
        this.$message("您已在教学中...");
        return;
      }
      if (this.$store.state.user.u_name.u_authentication == "0") {
        this.$message({ message: "您还没有通过平台认证", type: "warning" });
        return;
      }
      this.axios
        .post("/t/sub/addSubscribe", {
          s_user_id: this.$store.state.user.u_name.u_id,
          s_user_name: this.$store.state.user.u_name.u_name,
          s_user_image: this.$store.state.user.u_name.u_image,
          s_order_id: this.user.o_id,
          s_order_name: this.user.o_name,
          s_order_address: this.user.o_address,
        })
        .then((resp) => {
          if (resp.data == "1") {
            this.$confirm("预约成功！");
          } else {
            this.$confirm(resp.data);
          }
        });
    },

    /**
     * 推荐等tab切换
     * @param tab
     * @param event
     */
    handleClick(tab, event) {
      console.log(tab, event);
    },
    toOrderInfo(id) {
      //只有老师才可以打开弹框
      if (this.$store.state.user.u_name.u_role == "2") {
        this.showOrderInfo = true;
        this.axios
          .get("/order/getOrderById", {
            params: {
              id: id,
            },
          })
          .then((resp) => {
            this.user = resp.data;
            this.user.o_phone =
              resp.data.o_phone.substring(0, 3) +
              "****" +
              resp.data.o_phone.substring(7, 11);
          });
      }
    },
    loadOrder() {
      this.axios.get("/order/newOrder").then((resp) => {
        if (resp.data.length >= 10) {
          this.orderList = resp.data.slice(0, 10);
        } else {
          this.orderList = resp.data;
        }
      });
    },
    toTeacherInfo(id) {
      this.$router.push({
        path: `/teacherInfo/${id}`,
      });
    },
    loadTeacher() {
      this.axios
        .get("/user/findTeacherByAddress", {
          params: { address: this.$store.state.user.u_name.u_address_name },
        })
        .then((resp) => {
          if (resp.data.length >= 10) {
            this.teacherList = resp.data.slice(0, 10);
          } else {
            this.teacherList = resp.data;
          }
        })
        .catch((resp) => {
          //this.$confirm("网络异常")
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}
.cale {
  //width: 100px;
  height: 100px;
}
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
  height: 200px;
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
  font-size: 20px;
  // font-style: italic;
  min-height: 30px;
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
//卡片样式
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
}
//文本样式区
.name-role {
  font-size: 16px;
  padding: 5px;
  text-align: center;
}
.sender {
  text-align: center;
  font-family: 华文行楷;
  color: #303133;
  font-size: 25px;
}
.registe-info {
  text-align: center;
  padding-top: 10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%;
}

.relation-item {
  padding: 12px;
}
.dialog-footer {
  padding-top: 10px;
  padding-left: 10%;
}
//布局样式区
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
