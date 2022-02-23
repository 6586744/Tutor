<template>
  <div>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="预约他的老师"  />
    <el-table :data="t_subList">

      <el-table-column prop="s_user_name" label="教师名" >
      </el-table-column>
      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="toTeacherInfo(scope.row)">详情</el-button>
          <el-button type="text" size="small" @click="selectionTeacher(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="他预约的老师"  />
    <el-table :data="u_subList">
      <el-table-column prop="s_user_name" label="教师名" >
      </el-table-column>

      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="toTeacherInfo(scope.row)">详情</el-button>
          <el-button type="text" size="small" @click="selectionTeacher(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px" :closable="false" title="推荐的老师"  />
    <el-table :data="teacherList">
      <!--      设置一个隐藏域用来保存id-->
      <!--      <el-table-column prop="t_id" label="id" />-->
      <el-table-column prop="u_name" label="教师名" >
      </el-table-column>

      <el-table-column prop="time" label="创建时间" />
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="toTeacherInfo(scope.row)">详情</el-button>
          <el-button type="text" size="small" @click="selectionTeacher1(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button style="float: right; margin-top: 20px" @click="back()"><i class="el-icon-back"></i>返回</el-button>
  </div>

</template>

<script>
export default {
  name: "subscribeInfo",
  props:['orderId'],
  data() {
    return {
      t_subList:[],
      u_subList: [],
      teacherList : [],
    }
  },

  methods :{
    selectionTeacher1(row) {
      this.axios.post(
        '/order/addOrderTeacher',
        {
          o_id: this.$route.params.orderId,
          o_teacher_id : row.u_id,
          o_teacher_name : row.u_name,
          o_teacher_image : row.u_image,
          o_status: '1',
        }
      ).then(
        (resp) => {
          this.$confirm("选择成功！");
          window.history.back(-1)
        }
      )
    },

    /**
     * 老师类型的用户需要有一个字段判断是否在教学中，如果在教学中，那么将无法预约和被查看。
     * 这个字段在修改订单状态为对接中的时候进行改变，===1；订单状态为带评论的时候，这个字段将修改为===0；
     * @param row
     */
    selectionTeacher(row) {
      this.axios.post(
        '/order/addOrderTeacher',
        {
          o_id: this.$route.params.orderId,
          o_teacher_id : row.s_user_id,
          o_teacher_name : row.s_user_name,
          o_teacher_image : row.s_user_image,
          o_status: '1',
        }
      ).then(
        (resp) => {
          this.$confirm("选择成功！");
          window.history.back(-1)
        }
      )
    },

    toTeacherInfo(row) {
      this.$router.push({
        path:`/previewUser/${row.s_user_id}`
      })
    },

    loadTSubList(id) {
      this.axios.post(
        '/t/sub/queryOrder', {
          s_order_id: id
        }
      ).then(
        (resp) => {
          this.t_subList = resp.data
        }
      )
    },
    loadUSubList(id) {
      this.axios.post(
        '/u/sub/queryOrder', {
          s_order_id: id
        }
      ).then(
        (resp) => {
          this.u_subList = resp.data
        }
      )
    },
    selectTeacherForOrder(id) {
      this.axios.get(
        '/user/selectTeacherForOrder', {
          params : {orderId: id}
        }
      ).then(
        (resp) => {
          this.teacherList = resp.data;
        }
      )
    },
    back() {
      window.history.back(-1)
    },
  },

  mounted() {
    let id = this.$route.params.orderId;
    this.loadTSubList(id);
    this.loadUSubList(id);
    this.selectTeacherForOrder(id);
  }
}
</script>

<style scoped>

</style>
