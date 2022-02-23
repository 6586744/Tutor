<template>
  <div style="width: 70%; margin: 0 auto">
    <el-row :gutter="20" style="margin-top:10px;">
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>用户详情</span>
            </div>
            <div class="name-role">
              <span class="sender">{{user.u_name}}</span>
            </div>
            <div class="name-role">
              <el-avatar style="text-align: center" :size="60">
                <img :src="user.u_image"/>
              </el-avatar>
            </div>
            <p v-if="user.u_authentication == '0'" style="text-align: center; font-size: 20px; font-family: 楷体">
              <i style="color: red" class="el-icon-close"></i>未认证
            </p>
            <p v-if="user.u_authentication == '1'" style="text-align: center; font-size: 20px; font-family: 楷体">
              <i style="color: #5cb85c" class="el-icon-check"></i>已认证
            </p>
            <div class="registe-info">

          <span class="registe-info">
            注册时间：
            <li class="fa fa-clock-o"></li>
             {{user.time}}
          </span>
            </div>
            <el-divider></el-divider>
            <div class="personal-relation">
              <div class="relation-item">手机号:  <div style="float: right; padding-right:20px;">{{user.u_phone}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">昵称:  <div style="float: right; padding-right:20px;">{{user.u_show}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">邮箱:  <div style="float: right; padding-right:20px;">{{user.u_email}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">性别:  <div style="float: right; padding-right:20px;">{{user.u_sex}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">地址:  <div style="float: right; padding-right:20px;">{{user.u_address_name}}</div></div>
            </div>
            <div class="personal-relation">
              <div class="relation-item">备注:  <div style="float: right; padding-right:20px;">{{user.u_remake}}</div></div>
            </div>
            <el-row style="text-align: center">
              <el-button @click="back()">返 回</el-button>
              <el-button v-if="userRole == '1'" type="primary" @click="showUserSubscribe()">预 约</el-button>
              <el-button v-if="userRole == '2'" plain disabled type="primary" @click="showUserSubscribe()">预 约</el-button>
            </el-row>
          </el-card>
        </div>
        <baidu-map style="width: 400px; height: 400px; margin-top: 20px" :center="baidu_map" :zoom="15">
          <bm-marker :position="baidu_map" :dragging="true" animation="BMAP_ANIMATION_BOUNCE">
            <bm-label :content="user.u_name" :labelStyle="{color: 'red', fontSize : '24px'}" :offset="{width: -35, height: 30}"/>
          </bm-marker>
        </baidu-map>
      </el-col>
      <el-col :span="16">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>教育经历</span>
            </div>
            <div>
              <el-table :data="userEducation">
                <!--      设置一个隐藏域用来保存id-->
                <!--      <el-table-column prop="t_id" label="id" />-->
                <el-table-column prop="e_school" label="院校或机构" >
                </el-table-column>
                <el-table-column prop="e_academic" label="专业" >
                </el-table-column>
                <el-table-column prop="e_start_time" label="开始时间" >
                </el-table-column>
                <el-table-column prop="e_end_time" label="结束时间" >
                </el-table-column>
                <el-table-column prop="e_certificate" label="详情" >
                </el-table-column>
                <el-table-column prop="time" label="创建时间" />
              </el-table>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col style="margin-top: 20px" :span="16">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>教学经验</span>
            </div>
            <div>
              <el-table :data="userExperience">
                <el-table-column prop="e_address" label="所教科目" >
                </el-table-column>
                <el-table-column prop="e_start_time" label="开始时间" >
                </el-table-column>
                <el-table-column prop="e_end_time" label="结束时间" >
                </el-table-column>
                <el-table-column prop="e_remake" label="详情" >
                </el-table-column>
                <el-table-column prop="time" label="创建时间" />
              </el-table>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col style="margin-top: 20px; float: right" :span="16">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>擅长科目</span>
            </div>
            <div>
              <el-table :data="userExpert">
                <el-table-column prop="e_big_type_name" label="详情" >
                </el-table-column>
                <el-table-column prop="time" label="创建时间" />
              </el-table>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col style="margin-top: 20px; float: right" :span="16">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>历史评价</span>
            </div>
            <div>
              <el-table :data="commentList.slice((currentPage-1)*PageSize,currentPage*PageSize)">
                <el-table-column prop="c_content" label="评价内容" >
                </el-table-column>
                <el-table-column prop="c_user_show" label="用户昵称" >
                </el-table-column>
                <el-table-column prop="time" label="时间" >
                </el-table-column>
              </el-table>
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
          </el-card>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="选择订单" :visible.sync="showOrderInfo">
      <el-table :data="orderList">
        <el-table-column prop="o_name" label="学生" >
        </el-table-column>
        <el-table-column prop="o_type" label="科目" >
        </el-table-column>
        <el-table-column prop="o_sex" label="性别" >
        </el-table-column>
        <el-table-column prop="o_age" label="年龄" >
        </el-table-column>
        <el-table-column prop="time" label="创建时间" />
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="primary" @click="addUserSubscribe(scope.row)">预 约</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showOrderInfo = false">取 消</el-button>

      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props:['userId'],
  data(){
    return{
      dataForm:{
        nickName: '超级管理员',
        phone: '173567777777',
        homeUrl: 'http://www.baidu.com'
      },

      userEducation:[],
      userExperience: [],
      userExpert: [],
      //教师信息对象
      user: {
        u_id: '',
        u_name: '',
        u_sex: '',
        u_phone: '',
        u_password: '',
        u_image: '',
        u_show: '',
        u_email: '',
        u_address_id: '',
        u_address_name: '',
        u_baidu_address: '',
        u_remake: '',
        u_status: '',
        time: '',
        u_authentication: '',
      },

      /**
       * 家长预约相关。
       * 需要先获取家长所有订单的表格。、
       * 然后选择一个订单去预约、
       */
      showOrderInfo: false,
      orderList: [],
      userRole: '',

      commentList:[],
      currentPage:1,
      pageSizes:[1,2,3,4,5],
      totalCount:1,
      // 个数选择器（可修改）
      PageSize:3,

      baidu_map: {lng: '', lat: ''},
    }
  },
  methods : {

    showUserSubscribe() {
      this.showOrderInfo = true;
      this.axios.post(
        '/order/queryOrder', {
          o_student_id: this.$store.state.user.u_name.u_id,
          o_status: '0',
        }
      ).then(
        (resp) => {
          this.orderList = resp.data;
        }
      )
    },
    addUserSubscribe(row) {
      this.axios.post(
        "/u/sub/addSubscribe", {
          s_user_id : this.user.u_id,
          s_user_name : this.user.u_name,
          s_user_image : this.user.u_image,
          s_order_id : row.o_id,
          s_order_name : row.o_name,
          s_order_address: row.o_address,
        }
      ).then(
        (resp) => {
          if(resp.data == '1') {
            this.$confirm("预约成功！");
            this.showOrderInfo = false;
          }
          else  {
            this.$confirm(resp.data);
          }
        }
      )
    },


    back() {
      window.history.back(-1)
    },
    loadUserEducation(id) {
      this.axios.post(
        '/education/queryEducation',
        {
          e_teacher_id: id,
          //this.$store.state.user.u_name.u_id
        }
      ).then(
        (resp) => {
          for(let i = 0; i < resp.data.length; i ++) {
            // console.log(resp.data[i].e_start_time)
            resp.data[i].e_start_time = resp.data[i].e_start_time.substring(0, 7)
            resp.data[i].e_end_time = resp.data[i].e_end_time.substring(0, 7)
          }
          this.userEducation = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
    loadUserExperience(id) {
      this.axios.post(
        '/experience/queryExperience',
        {
          e_teacher_id: id
        }
      ).then(
        (resp) => {
          for(let i = 0; i < resp.data.length; i ++) {
            // console.log(resp.data[i].e_start_time)
            resp.data[i].e_start_time = resp.data[i].e_start_time.substring(0, 7)
            resp.data[i].e_end_time = resp.data[i].e_end_time.substring(0, 7)
          }
          this.userExperience = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
    loadUserExpert(id) {
      this.axios.post(
        '/expert/queryExpert',
        {
          e_teacher_id: id
        }
      ).then(
        (resp) => {
          this.userExpert = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
    loadUserInfo(id) {
      this.axios.get(
        '/user/getUserById',
        {
          params: {userId: id}
        }
      ).then(
        (resp) => {
          this.user = resp.data
          let mapString = resp.data.u_baidu_address;
          let mapList = mapString.split(',');
          this.baidu_map.lng = mapList[0];
          this.baidu_map.lat = mapList[1];
          console.log(resp.data.u_phone.substring(0,2) + "****" + resp.data.u_phone.substring(7,11))
          this.user.u_phone = resp.data.u_phone.substring(0,3) + "****" + resp.data.u_phone.substring(7,11)
        }
      )
    },
    loadUserComment(id) {
      this.axios.post(
        '/comment/queryComment', {
          c_teacher_id: id
        }
      ).then(
        (resp) => {
          this.commentList =resp.data;
          this.totalCount = resp.data.length;
        }
      )
    },
    handleSizeChange(val) {
      this.PageSize=val
      this.currentPage=1
    },
    // 显示第几页
    handleCurrentChange(val) {
      this.currentPage=val
    },
  },
  mounted() {
    this.userRole = this.$store.state.user.u_name.u_role;
    let id = this.$route.params.userId //注意这种操作传参
    this.loadUserEducation(id)
    this.loadUserExperience(id)
    this.loadUserExpert(id)
    this.loadUserInfo(id)
    this.loadUserComment(id)
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
</style>
