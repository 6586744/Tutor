<template>
    <div>
      <el-alert style="background-color: #409EFF; color: #f0f2f5; font-style: unset; margin-top: 10px; margin-bottom: 20px"
                :closable="false"
                title="预约我的"  />
      <el-button @click="back()">返 回</el-button>
      <span>订单号：{{this.$route.params.orderId}}</span>
      <el-table :data="subscribeList">
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

      <el-dialog title="预览" width="30%"  :visible.sync="showUserImg">
        <el-image :src="imageUrl" ></el-image>
      </el-dialog>
    </div>
</template>

<script>
export default {
  name: "userCheckSubscribe",
  props:["orderId"],
  data() {
    return {
      subscribeList: [],
      imageUrl: '',
      showUserImg:false,
    }
  },
  methods : {
    toTeacherInfo(row) {
      this.$router.push({
        path:`/teacherInfo1/${row.s_user_id}`
      })
    },
    back() {
      window.history.back(-1)
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
        '/t/sub/queryOrder', {
          s_order_id: this.$route.params.orderId,
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

<style scoped>

</style>
