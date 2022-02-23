<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="订单列表"  />

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="o_id" label="订单号" >
      </el-table-column>
      <el-table-column prop="o_student_name" label="发布者" >
      </el-table-column>
      <el-table-column prop="o_name" label="学生姓名" >
      </el-table-column>
      <el-table-column prop="o_type" label="课程类型" >
      </el-table-column>
      <el-table-column prop="o_teacher_name" label="教师" >
      </el-table-column>
      <el-table-column prop="a_name" label="学生押金" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_user_status == '1'" >已交</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_user_status == '0'" >未交</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="a_name" label="教师押金" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_teacher_status == '1'" >已交</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_teacher_status == '0'" >未交</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="o_phone" label="联系方式" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_status == '0'" >已屏蔽</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '1'" >已屏蔽</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '2'" >{{scope.row.o_phone}}</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '3'" >{{scope.row.o_phone}}</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '4'" >{{scope.row.o_phone}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="a_name" label="订单状态" >
        <template slot-scope="scope">
          <el-tag type="success" size="small" plain v-if="scope.row.o_status == '0'" >寻找中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '1'" >对接中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '2'" >教学中</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '3'" >待评价</el-tag>
          <el-tag type="danger" size="small" plain v-if="scope.row.o_status == '4'" >已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button type="text" v-if="scope.row.o_status == '1'  && scope.row.o_teacher_status == '0'" size="small" @click="addUserStatus(scope.row)">缴纳押金</el-button>
          <el-button type="text" v-if="scope.row.o_status == '1'" size="small" @click="updateOrderTeacher(scope.row)">取消对接</el-button>
          <el-button type="text" v-if="scope.row.o_status == '1'" size="small" @click="openOrderInfo(scope.row)">查看订单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="详情" :visible.sync="showOrderInfo">
      <div class="grid-content bg-purple">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>订单详情</span>
          </div>
          <div class="name-role">
            <span class="sender">{{user.o_student_name}}</span>
          </div>
          <div class="name-role">
            <el-avatar style="text-align: center" :size="60">
              <img :src="user.o_student_image"/>
            </el-avatar>
          </div>
          <div class="registe-info">
          <span class="registe-info">
            发布时间：
            <li class="fa fa-clock-o"></li>
             {{user.time}}
          </span>
          </div>
          <el-divider></el-divider>
          <div class="personal-relation">
            <div class="relation-item">学生姓名:  <div style="float: right; padding-right:20px;">{{user.o_name}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">联系方式:  <div style="float: right; padding-right:20px;">{{user.o_phone}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">学生性别:  <div style="float: right; padding-right:20px;">{{user.o_sex}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">学生年龄  <div style="float: right; padding-right:20px;">{{user.o_age}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">科目:  <div style="float: right; padding-right:20px;">{{user.o_type}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">价格:  <div style="float: right; padding-right:20px;">{{user.o_price}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">上课时间:  <div style="float: right; padding-right:20px;">{{user.o_time}}</div></div>
          </div>
          <div class="personal-relation">
            <div class="relation-item">备注:  <div style="float: right; padding-right:20px;">{{user.o_reamke}}</div></div>
          </div>
        </el-card>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showOrderInfo = false">取 消</el-button>
      </div>
    </el-dialog>
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
  </div>
</template>

<script>
export default {
  name: "AllType",
  data() {
    return {
      dialogFormVisible: false,
      updateForm : false,
      //添加表单的数据
      tableData: [],
      // 默认显示第几页
      currentPage:1,
      // 个数选择器（可修改）
      pageSizes:[1,2,3,4,5,6,7,8,9,10],
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:10,

      /**
       * 查看订单
       */
      showOrderInfo: false,
      user: {},
    };
  },

  mounted() {
    this.loadTeacher();
  },

  methods : {

    openOrderInfo(row) {
      this.showOrderInfo = true
      this.axios.get(
        '/order/getOrderById',
        {
          params: {
            id: row.o_id,}
        }
      ).then(
        (resp) => {
          this.user = resp.data;
          this.user.o_phone = resp.data.o_phone.substring(0,3) + "****" + resp.data.o_phone.substring(7,11)
        }
      )
    },

    /**
     * 取消对接这个订单
     */
    updateOrderTeacher(row) {
      this.axios.post(
        '/order/addOrderTeacher', {
          o_id: row.o_id,
          o_teacher_id : row.o_teacher_id,
          o_teacher_name : '',
          o_teacher_image : '',
          o_status: '0',
          o_user_status : '0',
          o_teacher_status: "0",
        }
      ).then(
        (resp) => {
          this.$confirm("取消成功");
          this.load();
        }
      )
    },

    /**
     * 这里假装调用了支付接口、
     */
    addUserStatus(row) {
      this.axios.post(
        '/order/deposit', {
          o_id: row.o_id,
          o_teacher_status: '1'
        }
      ).then(
        (resp) => {
          this.$confirm("押金缴纳成功");
          this.loadTeacher()
        }
      )
    },
    /**
     */
    // 分页
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    handleCurrentChange(val) {
      this.currentPage=val
    },

    loadTeacher() {
      this.axios.post(
        '/order/queryOrder', {
          o_teacher_id: this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          this.tableData = resp.data;
          this.totalCount = resp.data.length
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },

  }
}
</script>

<style lang="scss" scoped>
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
  font-style: italic;
  font-family: 华文行楷;
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
  clear: both
}

.box-card {
  width: 100%;
}
//文本样式区
.name-role {
  font-size: 16px;
  padding: 5px;
  text-align:center;
}
.sender{
  text-align:center;
  font-family: 华文行楷;
  color: #303133;
  font-size: 25px;
}
.registe-info{
  text-align: center;
  padding-top:10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%
}

.relation-item {
  padding: 12px;

}
.dialog-footer{
  padding-top:10px ;
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

