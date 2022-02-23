<template>
  <div style="width: 70%; margin: 0 auto; text-align: center;">
    <el-alert
      style="margin-top: 20px;"
      title="价格一览表"
      type="success"
      :closable="false"
    >
    </el-alert>
    <el-col style="margin-top: 20px; text-align: center; margin-bottom: 20px" >
      <div style="width: 100%; text-align: center;">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>价格列表</span>
          </div>
          <div>
            <el-table :data="priceList">
              <el-table-column prop="p_name" label="科目名称" >
              </el-table-column>
              <el-table-column prop="p_start_price" label="最低价格" >
              </el-table-column>
              <el-table-column prop="p_end_price" label="最高价格" >
              </el-table-column>
              <el-table-column prop="time" label="创建时间" />
            </el-table>
          </div>
        </el-card>
      </div>
    </el-col>
  </div>
</template>

<script>
export default {
  name: "price",
  data() {
    return {
      priceList: [],
    }
  },

  methods: {
    loadPrice() {
      this.axios.get(
        '/price/getAllPrice',
      ).then(
        (resp) => {
          this.priceList = resp.data;
        }
      ).catch(
        (resp) => {
          this.$confirm("网络异常！");
        }
      )
    },
  },
  mounted() {
    this.loadPrice()
  }
}
</script>

<style scoped>

</style>
