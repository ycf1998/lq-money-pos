<template>
  <div style="padding: 20px 50px">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>订单状态：<el-tag :type="statusTag(order.status)">{{ dict.label.orderStatus[order.status] }}</el-tag></span>
      </div>
      <!-- 基本信息 -->
      <el-descriptions class="margin-top" title="基本信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-promotion" />
            订单号
          </template>
          {{ order.orderNo }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user" />
            会员名
          </template>
          {{ order.member }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom" />
            会员类型
          </template>
          {{ dict.label.memberType[member.type] || '非会员' }}
        </el-descriptions-item>
      </el-descriptions>
      <!-- 商品信息 -->
      <div>
        <h4>商品信息</h4>
        <el-table row-key="id" :data="orderDetail" border style="width: 100%" :summary-method="getSummaries" show-summary>
          <el-table-column prop="goodsBarcode" label="条码" align="center" />
          <el-table-column prop="goodsName" label="名称" align="center" />
          <el-table-column prop="quantity" label="数量" align="center" />
          <el-table-column prop="salePrice" label="售价" align="center" />
          <el-table-column prop="salePoints" label="消耗积分" align="center" />
          <el-table-column prop="goodsPrice" label="应收" align="center" />
          <el-table-column prop="subTotal" label="小计" align="center">
            <template slot-scope="{ row }">
              {{ calculator.Mul(row.quantity, row.goodsPrice) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width" fixed="right">
            <template slot-scope="{ row }">
              <el-button type="danger" size="mini" @click="handleReturnGoods(row)">退货</el-button>
              <span v-if="row.returnQuantity > 0" class="note"> ( 已退X{{ row.returnQuantity }} )</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!-- 费用信息 -->
      <el-descriptions class="margin-top" title="费用信息" :column="4" border direction="vertical">
        <el-descriptions-item>
          <template slot="label">总计</template>
          {{ order.totalAmount }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">消耗积分</template>
          <span>{{ order.pointsAmount }}
            <span v-if="returnPoints > 0" class="note"> ( -{{ returnPoints }} )</span>
          </span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">支付</template>
          <span style="font-size: 18px;color: red">￥{{ order.payAmount }}
            <span v-if="returnPrice > 0" class="note"> ( -{{ returnPrice }} )</span>
          </span>
        </el-descriptions-item>
      </el-descriptions>
      <!-- 操作记录 -->
      <div>
        <h4>操作记录</h4>
        <el-table row-key="id" :data="log" border style="width: 100%">
          <el-table-column prop="description" label="操作描述" align="center">
            <template slot-scope="{ row }">
              <span v-html="row.description" />
            </template>
          </el-table-column>
          <el-table-column prop="createBy" label="操作人" align="center" />
          <el-table-column prop="createTime" label="操作时间" align="center" />
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import orderApi from '@/api/oms/order'
import calculator from '@/utils/calculator'

export default {
  name: 'OrderDetail',
  dicts: ['orderStatus', 'memberType'],
  data() {
    return {
      calculator: calculator,
      order: {},
      orderDetail: [],
      member: {},
      log: [],
      returnPrice: 0,
      returnPoints: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      const id = this.$route.params && this.$route.params.id
      orderApi.getDetial(id).then((res) => {
        this.order = res.data.order
        this.orderDetail = res.data.orderDetail
        this.log = res.data.orderLog
        this.member = res.data.member
        this.orderDetail.forEach((e) => {
          e.visible = false
          e.tempReturn = 1
        })
      })
    },
    // 状态颜色
    statusTag(status) {
      if (status === 'RETURN') {
        return 'info'
      } else if (['PAID', 'DONE'].includes(status)) {
        return 'success'
      }
    },
    // 合计
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价'
          return
        }
        if (column.property === 'subTotal') {
          let values = data.map((item) => Number(item['quantity'] * item['goodsPrice']))
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          values = data.map((item) => Number(item['returnQuantity'] * item['goodsPrice']))
          this.returnPrice = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          values = data.map((item) => Number(item['returnQuantity'] * item['points']))
          this.returnPoints = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
        }
      })
      return sums
    },
    // 退货
    handleReturnGoods(row) {
      this.$prompt('请输入退货数量', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\d+/,
        inputErrorMessage: '请输入正数数'
      }).then(({ value }) => {
        if (value > parseInt(row.quantity) - parseInt(row.returnQuantity)) {
          this.$notify({
            title: 'Warning',
            message: '退货数量不能超过剩余商品数量',
            type: 'warning',
            duration: 2000
          })
          return
        }
        const params = {
          id: row.id,
          quantity: value
        }
        orderApi.returnGoods(params).then(() => {
          this.$notify({
            title: 'Success',
            message: '退货成功',
            type: 'success',
            duration: 2000
          })
          this.load()
        })
      })
    }
  }
}
</script>

<style scoped>
.note {
  color: #909399;
}
.margin-top {
  margin-top: 20px;
}
</style>
