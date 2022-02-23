<template>
  <div style="width: 70%; margin: 0 auto">
    <div class="comment-list">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="所有老师" name="1"></el-tab-pane>
        <el-tab-pane label="同一区域" name="2"></el-tab-pane>
        <el-tab-pane v-if="u_role == '1'" label="擅长推荐" name="3"></el-tab-pane>
<!--        <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>-->
      </el-tabs>
      <div class="top-title">
        <span>{{orderList.length}} 个老师</span>
      </div>
      <div

        v-for="(item, i) in orderList.slice((currentPage-1)*PageSize,currentPage*PageSize)"
        :key="i"
        class="item"
      >
        <table><tr>
          <td>
            <div style="margin-left: 10px" class="item-header">
              <div class="author">
                <div class="demo-basic--circle">
                  <div class="block"><el-avatar size="medium" :src="item.u_image"></el-avatar></div>
                  <!--                </div>-->
                </div>
              </div>
              <div class="info" style="width: 80%; height: 120px;float: left" >
                <div class="name">
                  {{item.u_name}}
                </div>
                <div class="time">
                  {{item.time}}
                </div>
              </div>
            </div>
          </td>
          <td>
            <div @click="toTeacherInfo(item.u_id)" class="comment-detail" >
              <p>性别：{{item.u_sex}}</p>
              <!--          <p>教学科目：{{item.o_type}}</p>-->
              <!--          <p>教学科目：{{item.o_type}}</p>-->
              <p>地址：{{item.u_address_name}}</p>
              <p>备注：{{item.u_remake}}</p>
              <!--          <a @click="toPreviewBlog(item.b_id)">{{item.b_titile}}</a>-->
            </div>
          </td>
        </tr></table>
      </div>
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        style="align-content: center; align-items: center"
        :current-page="currentPage"
        :page-sizes="pageSizes"
        :total="totalCount"
        :page-size="PageSize"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      ></el-pagination>
      <!--        <div style="width: 25%; height: 200px;display:inline;float:left;">-->
      <!--          <img style="width:300px; height: 90%" src="../../assets/background.jpg"/>-->
      <!--        </div>-->
      <!--      </div>-->

    </div>

  </div>
</template>

<script>
export default {
  name: "newOrder",
  data() {
    return {
      orderList: [],
      // 默认显示第几页
      currentPage:1,
      // 个数选择器（可修改）
      pageSizes:[1,2,3,4,5,6,7,8,9,10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:10,
      activeName: '1',
      u_role:'',
    }
  },
  methods : {
    /**
     * 推荐等tab切换
     * @param tab
     * @param event
     *         <el-tab-pane label="所有老师" name="1"></el-tab-pane>
     <el-tab-pane label="同一区域" name="2"></el-tab-pane>
     <el-tab-pane label="擅长推荐" name="3"></el-tab-pane>
     */
    handleClick(tab, event) {
      console.log(tab, event);
      if(this.activeName == '1') {
        this.load();
      }
      if(this.activeName == '2') {
        this.findTeacherByAddress();
      }
      if(this.activeName == '3') {
        this.expertTeacherForOrder();
      }
    },
    findTeacherByAddress() {
      this.axios.get(
        '/user/findTeacherByAddress', {
          params: {address: this.$store.state.user.u_name.u_address_name}
        }
      ).then(
        (resp) => {
          this.orderList = resp.data;
        }
      )
    },
    expertTeacherForOrder() {
      this.axios.get(
        '/user/expertTeacherForOrder', {
          params: {userId: this.$store.state.user.u_name.u_id}
        }
      ).then(
        (resp) => {
          this.orderList = resp.data;
        }
      )
    },

    toTeacherInfo(id) {
      this.$router.push({
        path:`/teacherInfo/${id}`
      })
    },
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.currentPage=val
    },
    load() {
      this.axios.post(
        '/user/queryUser',
        {
          u_authentication: '1',
          u_teachering: '0', //需要寻找未在教学中的老师。
        }
      ).then(
        (resp) => {
          this.orderList = resp.data;
          console.log(resp.data)
          // 将数据的长度赋值给totalCount

          this.totalCount = resp.data.length
        }
      )
    },
  },
  mounted() {
    this.load();
    this.u_role = this.$store.state.user.u_name.u_role
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
  overflow: auto;
  font-size: 18px;
  /* font-style: italic; */
  /* font-family: 华文行楷; */
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
