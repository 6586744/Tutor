<template>
  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px"
              :closable="false"
              title="预约我的"  />
    <el-table :data="subscribeList">
      <el-table-column prop="s_order_id" label="订单号" >
      </el-table-column>
      <el-table-column prop="s_user_name" label="教师名" >
      </el-table-column>
      <el-table-column prop="s_user_name" label="头像" >
        <template slot-scope="scope">
          <el-button type="success" size="small" plain @click="mouseOver(scope.row)" >预览</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="s_order_name" label="学生名字" >
      </el-table-column>
      <el-table-column prop="s_order_address" label="学生地址" >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="toTeacherInfo(scope.row)">查看详情</el-button>
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
        <el-button type="primary" @click="addTeacherSubscribe()">预 约</el-button>
      </div>
    </el-dialog>
    <el-dialog title="预览" width="30%"  :visible.sync="showUserImg">
      <el-image :src="imageUrl" ></el-image>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "userCheckSubscribe",
  data() {
    return {
      subscribeList: [],
      imageUrl: '',
      showUserImg:false,

      /**
       * 订单详情相关
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
    }
  },
  methods : {
    toTeacherInfo(row) {
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
    /**
     * 教师预约学生
     */
    addTeacherSubscribe() {
      if(this.$store.state.user.u_name.u_teachering == '1') {
        this.$message("您已在教学中...")
        return ;
      }
      if(this.$store.state.user.u_name.u_authentication == '0') {
        this.$message({message: "您还没有通过平台认证", type: 'warning'});
        return ;
      }
      this.axios.post(
        '/t/sub/addSubscribe',
        {
          s_user_id : this.$store.state.user.u_name.u_id,
          s_user_name : this.$store.state.user.u_name.u_name,
          s_user_image : this.$store.state.user.u_name.u_image,
          s_order_id : this.user.o_id,
          s_order_name : this.user.o_name,
          s_order_address: this.user.o_address,
        }
      ).then(
        (resp) => {
          if(resp.data == '1') {
            this.$confirm("预约成功！");
          }
          else  {
            this.$confirm(resp.data);
          }
        }
      )
    },

    /**
     * 预览头像
     * @param row
     */
    mouseOver(row) {
      this.showUserImg = true;
      this.imageUrl = ''
      this.imageUrl = row.s_user_image
    },

    load() {
      this.axios.post(
        '/u/sub/queryOrder', { //注意这个接口是查询的预约他的学生
          s_user_id: this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          this.subscribeList = resp.data;
        }
      )
    }
  },
  mounted() {
    this.load();
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
