<template>

  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="我的预约"  />
    <el-divider></el-divider>

    <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)">
      <el-table-column prop="s_order_name" label="姓名" >
      </el-table-column>
      <el-table-column prop="s_order_address" label="地址" >
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="150px">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openSubInfoFrom(scope.row)">详情</el-button>
          <el-button type="text" size="small" @click="deleteType(scope.row)">取消预约</el-button>
        </template>
      </el-table-column>
    </el-table>

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

      /*
      详情相关
       */
      showOrderInfo: false,
      user : {
        o_id: '',
        o_student_id: '',
        o_student_name: '',
        o_student_image: '',
        o_name: '',
        o_phone: '',
        o_price: '',
        o_type: '',
        o_sex: '',
        o_age: '',
        o_reamke: '',
        o_address: '',
        o_time: '',
      }
    };
  },

  mounted() {
    this.load();
  },

  methods : {
    openSubInfoFrom(row) {
      this.showOrderInfo = true
      this.axios.get(
        '/order/getOrderById',
        {
          params: {
            id: row.s_order_id,}
        }
      ).then(
        (resp) => {
          this.user = resp.data;
          this.user.o_phone = resp.data.o_phone.substring(0,3) + "****" + resp.data.o_phone.substring(7,11)
        }
      )
    },
    deleteType(row) {
      this.axios.get(
        '/t/sub/deleteSubscribe',
        {
          params : {
            id : row.s_id
          }
        }
      ).then(
        (resp) => {
          this.$confirm("删除成功！");
          this.load()
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
    // 分页
    // 每页显示的条数
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    handleCurrentChange(val) {
      this.currentPage=val
    },

    load() {
      this.axios.post(
        '/t/sub/queryOrder', {
          s_user_id: this.$store.state.user.u_name.u_id
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
    }
  }
}
</script>

<style lang="scss" scoped>
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
