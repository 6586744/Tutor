
<template>
  <div class="mixin-components-container">
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; font-size: 30px" :closable="false" title="教师总览"  />
    <el-row :gutter="20" style="margin-top:50px;">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">总教师数量：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{teacherList.length}}</p>

          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">教学中：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{u_teachering}}</p>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">已认证：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{u_authentication}}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; font-size: 30px; margin-top: 30px" :closable="false" title="订单总览"  />
    <el-row :gutter="20" style="margin-top:50px;">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">总订单数量：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{orderList.length}}</p>

          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">寻找中：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{status0}}</p>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">对接中：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{status1}}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66">教学中：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{status2}}</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6" style="margin-top:20px">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="color: #5a5e66 ">已完成：</span>
          </div>
          <div class="component-item">
            <p style="font-size:25px; font-style: oblique; color: #409EFF">{{status3}}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      userName: '', //用户名

      teacherList: [],
      u_authentication: 0,
      u_teachering: 0,

      orderList:[],
      status0: 0,
      status1: 0,
      status2: 0,
      status3: 0,

    }
  },

  mounted() {
    this.userName = this.$store.state.user.userInfo.user.a_name;
    this.loadTeacher();
    this.loadOrder();
  },

  methods: {
    loadOrder() {
      this.axios.get(
        '/order/getAllOrder'
      ).then(
        (resp) => {
          for(let i = 0; i < resp.data.length; i ++) {
            if(resp.data[i].o_status == '0') this.status0 ++;
            if(resp.data[i].o_status == '1') this.status1 ++;
            if(resp.data[i].o_status == '2') this.status2 ++;
            if(resp.data[i].o_status == '3') this.status3 ++;
          }
          this.orderList = resp.data;
        }
      )
    },

      loadTeacher() {
        this.axios.post(
          '/user/queryUser', {
            u_role: '2'
          }
        ).then(
          (resp) => {
            for(let i = 0; i < resp.data.length; i ++) {
              if(resp.data[i].u_teachering == '1') this.u_teachering ++;
              if(resp.data[i].u_authentication == '1') this.u_authentication ++;
            }
            this.teacherList = resp.data;
          }
        )
      }
  },


}
</script>

<style lang="scss" scoped>
.mixin-components-container {
  background-color: #f0f2f5;
  padding: 30px;
  min-height: calc(100vh - 84px);
}
.component-item{
  min-height: 100px;
}
</style>
