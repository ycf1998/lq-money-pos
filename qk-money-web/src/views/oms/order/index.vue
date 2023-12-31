<template>
  <div class="app-container">
    <!-- 搜索 -->
    <div v-if="crud.props.searchToggle" class="filter-container">
      <!-- 【搜索条件】prop前缀都要加query. -->
      <el-date-picker v-model="datePicker" class="filter-item" type="datetimerange" :picker-options="pickerOptions" value-format="yyyy-MM-dd HH:mm:ss" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" align="right" style="margin-right:10px" />
      <el-input v-model="query.orderNo" placeholder="订单号" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-input v-model="query.member" placeholder="会员" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-select v-model="query.status" clearable placeholder="状态" class="filter-item-200" @change="crud.toQuery">
        <el-option v-for="item in dict.orderStatus" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <rr-operation />
    </div>
    <!-- CRUD操作 -->
    <crud-operation :hidden-columns="['createTime', 'remark']" />
    <!-- 列表 -->
    <el-table ref="table" v-loading="crud.loading" :summary-method="getSummaries" show-summary :data="crud.data" style="width: 100%;" @selection-change="crud.selectionChangeHandler" @sort-change="crud.sortChangeHandler">
      <!-- 【列控件】增加sortable支持排序 -->
      <el-table-column align="center" width="200" prop="orderNo" label="订单号" />
      <el-table-column align="center" prop="member" label="会员" />
      <el-table-column align="center" prop="costAmount" label="成本" />
      <el-table-column align="center" prop="totalAmount" label="总价" />
      <el-table-column align="center" prop="payAmount" label="实付款" />
      <el-table-column align="center" prop="profit" label="利润">
        <template slot-scope="{ row }">
          <span>{{ calculator.Sub(row.payAmount, row.costAmount) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="pointsAmount" label="消耗积分">
        <template slot-scope="{ row }">
          <span>{{ row.vip ? row.pointsAmount : 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态">
        <template slot-scope="{ row }">
          <el-tag :type="statusTag(row.status)">{{ dict.label.orderStatus[row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" width="180" prop="createTime" label="创建时间" />
      <el-table-column align="center" width="180" prop="paymentTime" label="支付时间" />
      <el-table-column align="center" prop="remark" label="备注" />

      <el-table-column label="操作" width="200" align="center" fixed="right">
        <template slot-scope="{ row }">
          <router-link :to="'/oms/order/detail/'+ row.id"><el-button size="mini">查看</el-button></router-link>
          <el-button v-if="row.status != 'RETURN'" size="mini" type="danger" @click="handleReturn(row, $index)">退单</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination />
  </div>
</template>

<script>
import rrOperation from '@/components/Crud/RR.operation.vue'
import crudOperation from '@/components/Crud/CRUD.operation.vue'
import Pagination from '@/components/Crud/Pagination.vue'
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
// 【引入对应的api文件】基础crud, 方法名必须为 add,edit,del
import orderApi from '@/api/oms/order'

import dayjs from 'dayjs'
import calculator from '@/utils/calculator'

export default {
  name: 'Order',
  components: { Pagination, rrOperation, crudOperation },
  cruds() {
    // 【url是查询接口】
    return CRUD({
      title: '订单',
      url: '/omsOrder',
      optShow: { add: false, edit: false, del: false }
    })
  },
  mixins: [presenter(), header(), form({}), crud()],
  dicts: ['orderStatus'],
  data() {
    return {
      countInfo: null,
      calculator: calculator,
      datePicker: [dayjs().startOf('M').format('YYYY-MM-DD HH:mm:ss'), dayjs().endOf('M').format('YYYY-MM-DD HH:mm:ss')],
      pickerOptions: {
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', [dayjs().startOf('d').toDate(), dayjs().endOf('d').toDate()])
            }
          },
          {
            text: '本月',
            onClick(picker) {
              picker.$emit('pick', [dayjs().startOf('M').toDate(), dayjs().endOf('M').toDate()])
            }
          },
          {
            text: '最近7天',
            onClick(picker) {
              picker.$emit('pick', [dayjs().subtract(6, 'd').startOf('d').toDate(), dayjs().endOf('d').toDate()])
            }
          },
          {
            text: '最近30天',
            onClick(picker) {
              picker.$emit('pick', [dayjs().subtract(29, 'd').startOf('d').toDate(), dayjs().endOf('d').toDate()])
            }
          }
        ]
      }
    }
  },
  methods: {
    // 查询前
    [CRUD.HOOK.beforeRefresh]() {
      this.query.startTime = this.datePicker[0]
      this.query.endTime = this.datePicker[1]
    },
    // 查询后
    [CRUD.HOOK.afterRefresh]() {
      orderApi
        .count({
          startTime: this.query.startTime,
          endTime: this.query.endTime
        })
        .then((res) => {
          this.countInfo = res.data
        })
    },
    // 退单
    handleReturn(row) {
      this.$confirm('确认退单?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        orderApi.returnOrder([row.id]).then(() => {
          this.$notify({
            title: 'Success',
            message: '退单成功',
            type: 'success',
            duration: 2000
          })
          this.crud.toQuery()
        })
      })
    },
    // 合计行
    getSummaries(param) {
      const { columns } = param
      const sums = []
      if (this.countInfo) {
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '总计'
            return
          }
          if (column.property === 'costAmount') {
            sums[index] = this.countInfo.costCount
          }
          if (column.property === 'payAmount') {
            sums[index] = this.countInfo.saleCount
          }
          if (column.property === 'profit') {
            sums[index] = this.countInfo.profit
          }
        })
      }
      return sums
    },
    // 状态颜色
    statusTag(status) {
      if (status === 'RETURN') {
        return 'info'
      } else if (status === 'PAID') {
        return 'success'
      }
    }
  }
}
</script>

