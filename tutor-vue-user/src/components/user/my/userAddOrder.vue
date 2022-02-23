<template>
  <div style="width: 70%; margin: 0 auto">
    <el-form :model="form">
      <el-form-item label="孩子姓名" :label-width="formLabelWidth">
        <el-input v-model="form.o_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="孩子性别" :label-width="formLabelWidth">
        <el-radio v-model="form.o_sex" label="男">男</el-radio>
        <el-radio v-model="form.o_sex" label="女">女</el-radio>
      </el-form-item>
      <el-form-item label="孩子年龄" :label-width="formLabelWidth">
        <el-input v-model="form.o_age" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" :label-width="formLabelWidth">
        <el-input v-model="form.o_phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="课时费" :label-width="formLabelWidth">
        <el-input v-model="form.o_price" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="选择地址" :label-width="formLabelWidth">
        <el-select v-model="form.o_address" placeholder="请选择">
          <el-option
            v-for="(item, index) in addressList"
            :key="index"
            :label="item.a_name"
            :value="item.a_name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选择科目" :label-width="formLabelWidth">
            <el-select v-model="form.o_type" >
              <el-option
                v-for="(item, index) in typeList"
                :key="index"
                :label="item.t_name"
                :value="item.t_name">
              </el-option>
            </el-select>
      </el-form-item>
      <el-form-item label="教学时间说明" :label-width="formLabelWidth">
        <el-input v-model="form.o_time" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教师要求" :label-width="formLabelWidth">
        <el-input v-model="form.o_reamke" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="addOrder()">确 定</el-button>
    </div>
  </div>
</template>
<!--1249944581-->
<script>
export default {
  name: "userAddOrder",
  data() {
    return {
      formLabelWidth: '150px',

      addressList:[],
      typeList: [],

      form: {
        o_student_id: '',
        o_student_name: '',
        o_student_image: '',
        o_name: '',
        o_type: '',
        o_sex: '男',
        o_age: '',
        o_reamke: '',
        o_address: '',
        o_time: '',
        o_phone:'',
        o_price:'',
      }
    }
  },
  methods: {
    addOrder() {
      this.axios.post(
        '/order/addOrder',
        {
          o_student_id: this.$store.state.user.u_name.u_id,
          o_student_name: this.$store.state.user.u_name.u_name,
          o_student_image: this.$store.state.user.u_name.u_image,
          o_name: this.form.o_name,
          o_type: this.form.o_type,
          o_sex:this.form.o_sex,
          o_age: this.form.o_age,
          o_reamke: this.form.o_reamke,
          o_address: this.form.o_address,
          o_time: this.form.o_time,
          o_phone: this.form.o_phone,
          o_price: this.form.o_price,
        }
      ).then(
        (resp) => {
          this.$confirm("添加成功");
        }
      )
    },

    loadAddress() {
      this.axios.get(
        '/address/getAllAddress',
      ).then(
        (resp) => {
          this.addressList = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
    loadType() {
      this.axios.get(
        '/type/getAllType',
      ).then(
        (resp) => {
          this.typeList = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
  },
  mounted() {
    this.loadType();
    this.loadAddress();
  }
}
</script>

<style scoped>

</style>
