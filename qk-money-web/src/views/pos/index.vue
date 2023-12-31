<template>
  <div class="app-container">
    <el-row :gutter="10">
      <!-- 工具栏 -->
      <el-col :sm="4" :md="3" :lg="2" style="float: right; margin-bottom: 10px;">
        <el-card class="tool-card" shadow="always">
          <div class="tool-list">
            <el-button plain @click="reload">刷新</el-button>
            <el-button v-if="isVip" plain type="success" @click="cancelVip">取消会员</el-button>
            <el-button plain :type="tool.editPrice ? 'success' : ''" @click="editPrice">修改价格</el-button>
            <el-button plain @click="clearOrderList">清空商品</el-button>
          </div>
        </el-card>
      </el-col>
      <!-- 收银台 -->
      <el-col class="cashier" :sm="20" :md="21" :lg="22">
        <!-- 输入区 -->
        <div class="cashier-input">
          <el-card shadow="always">
            <el-row :gutter="40" type="flex" class="row-bg" justify="space-between" align="middle">
              <el-col :xs="24" :sm="17" :md="19" :lg="20">
                <el-row type="flex" class="row-bg">
                  <el-autocomplete v-model="barcode" class="cashier-input-item" popper-class="cashier-input-item" :fetch-suggestions="queryGoods" placeholder="条码 or 名称" @select="item => barcode = item.barcode" @keydown.enter.native="enterBarcode">
                    <template slot-scope="{ item }">
                      <span class="label">{{ item.barcode }}</span>
                      <span class="desc">{{ item.name }} 🌰 {{ item.stock }}</span>
                    </template>
                  </el-autocomplete>
                </el-row>
                <el-row type="flex" class="row-bg" align="middle">
                  <el-autocomplete v-model="member" class="cashier-input-item" popper-class="cashier-input-item" :fetch-suggestions="queryMember" placeholder="会员名 or 手机号" @select="item => member = item.name" @keydown.enter.native="enterMember">
                    <template slot-scope="{ item }">
                      <span class="label">{{ item.name }}</span>
                      <span class="desc">{{ item.phone }} 🎫 {{ item.points }}</span>
                    </template>
                  </el-autocomplete>
                  <el-tag v-if="currentMember">积分：{{ currentMember.points }}</el-tag>
                </el-row>
              </el-col>
              <el-col :xs="0" :sm="7" :md="5" :lg="4">
                <el-button type="success" style="width:100%;height:100%" @click="showOrder">
                  <h1>收款</h1>
                  <h4>（空格space）</h4>
                </el-button>
              </el-col>
            </el-row>
          </el-card>
        </div>
        <!-- 购物车 -->
        <div class="cashier-goods">
          <el-card shadow="always">
            <el-row type="flex" justify="between">
              <el-col>
                <h4>共{{ total }}件</h4>
              </el-col>
              <el-col align="right">
                <h4>💰{{ totalAmount }} 🎫 {{ pointsAmount }} <span style="font-size:20px">🪙{{ payAmount }}</span>
                </h4>
              </el-col>
            </el-row>
            <el-table ref="table" border :data="orderList" style="width: 100%;" row-key="goodsBarcode">
              <el-table-column key="1" prop="goodsBarcode" align="center" label="条码" />
              <el-table-column key="2" prop="goodsName" align="center" label="商品" />
              <el-table-column key="3" prop="quantity" align="center" label="数量">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.quantity" size="small" :min="0" @change="changeQuantity(scope.row)" />
                </template>
              </el-table-column>
              <el-table-column key="4" prop="salePrice" align="center" label="原价" />
              <el-table-column key="5" prop="salePoints" align="center" label="消耗积分" />
              <el-table-column key="6" prop="goodsPrice" align="center" label="应收">
                <template slot-scope="scope">
                  <template v-if="tool.editPrice">
                    <el-input v-model="scope.row.goodsPrice" placeholder="请输入内容" class="input-with-select">
                      <el-button slot="append" icon="el-icon-refresh" @click="cancelEdit(scope.row)" />
                    </el-input>
                  </template>
                  <span v-else style="font-size: 16px">{{ scope.row.goodsPrice }}</span>
                </template>
              </el-table-column>
              <el-table-column key="7" prop="subcount" align="center" label="小计">
                <template slot-scope="scope">
                  <span style="font-size: 18px">{{ calculator.Mul(scope.row.quantity, scope.row.goodsPrice) }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!-- 确认清单 -->
    <el-dialog v-loading.fullscreen.lock="fullscreenLoading" class="orderDialog" title="清单" :visible.sync="showOrderDialog">
      <el-row :gutter="10" style="text-align: center">
        <el-col :span="6">
          <h4>商品</h4>
        </el-col>
        <el-col :span="6">
          <h4>原价</h4>
        </el-col>
        <el-col :span="6">
          <h4>现价</h4>
        </el-col>
        <el-col :span="6">
          <h4>消耗积分</h4>
        </el-col>
      </el-row>
      <div v-for="item in orderList" :key="item.barcode">
        <el-row style="text-align: center;margin-bottom: 10px;">
          <el-col :span="6"><span>{{ item.goodsName }}</span></el-col>
          <el-col :span="6"><span>{{ item.salePrice }}</span></el-col>
          <el-col :span="6"><span>{{ item.goodsPrice }}</span></el-col>
          <el-col :span="6"><span>{{ item.salePoints }}</span></el-col>
        </el-row>
        <el-row type="flex" style="text-align: center;margin-bottom: 20px;">
          <el-col><span style="color: #909399">数量 X {{ item.quantity }}</span></el-col>
          <el-col><span style="color: #909399">小计{{ calculator.Mul(item.goodsPrice, item.quantity) }}元</span></el-col>
          <el-col><span style="color: #909399">消耗积分{{ calculator.Mul(item.salePoints, item.quantity) }}</span>
          </el-col>
        </el-row>
      </div>
      <el-row style="text-align: center;margin-bottom: 15px;font-size: 15px">
        <el-col :span="8">总计：{{ totalAmount }}</el-col>
        <el-col :span="8">应付：{{ payAmount }}</el-col>
        <el-col :span="8">消耗积分：{{ pointsAmount }}</el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showOrderDialog = false"> 取消 </el-button>
        <el-button type="primary" @click="settleAccounts()"> 提交 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 精度计算
import calculator from '@/utils/calculator'
import posApi from '@/api/pos/pos'

export default {
  data() {
    return {
      calculator: calculator,
      // 当前订单
      orderList: [],
      // 所有商品
      goods: [],
      // 所有会员
      members: [],
      // 输入的条码
      barcode: null,
      // 输入的会员
      member: null,
      // 当前已选择的会员
      currentMember: null,
      // 是否是会员单
      isVip: false,
      // 显示确认清单
      showOrderDialog: false,
      // 整屏loading效果
      fullscreenLoading: false,
      // 工具栏
      tool: {
        // 修改价格
        editPrice: false
      }
    }
  },
  computed: {
    total: function () {
      return this.orderList.reduce((prev, next) => (prev.quantity | 0) + (next.quantity | 0), 0)
    },
    totalAmount: function () {
      let val = 0.0
      this.orderList.forEach((data) => {
        val += calculator.Mul(data.quantity, data.salePrice)
      })
      return val
    },
    payAmount: function () {
      let val = 0.0
      this.orderList.forEach((data) => {
        val += calculator.Mul(data.quantity, data.goodsPrice)
      })
      return val
    },
    pointsAmount: function () {
      let val = 0.0
      this.orderList.forEach((data) => {
        val += calculator.Mul(data.quantity, data.salePoints)
      })
      return val
    }
  },
  created() {
    // 空格收银
    const that = this
    document.onkeydown = function (e) {
      const key = window.event.keyCode
      if (key === 32) {
        e.preventDefault()
        that.showOrder()
      }
    }
    this.loadInit()
  },
  methods: {
    // 初始 or 刷新
    loadInit() {
      posApi.listMember(this.member).then((res) => {
        this.members = res.data
      })
      posApi.listGoods(this.barcode).then((res) => {
        this.goods = res.data
      })
    },
    // 查询商品
    queryGoods(barcodeOrName, cb) {
      let result = []
      if (barcodeOrName) {
        result = this.goods.filter((e) => e.barcode.includes(barcodeOrName) || e.name.includes(barcodeOrName))
      }
      cb(result.length > 10 ? [] : result)
    },
    // 查询会员
    queryMember(nameOrPhone, cb) {
      let result = []
      if (nameOrPhone) {
        result = this.members.filter((e) => e.name.includes(nameOrPhone) || e.phone.includes(nameOrPhone))
      }
      cb(result.length > 10 ? [] : result)
    },
    // 回车商品
    enterBarcode() {
      if (!this.barcode || (this.barcode && this.barcode.length < 1)) return
      const goods = this.goods.find((e) => e.barcode === this.barcode)
      if (goods) {
        let orderDetail = this.orderList.find((e) => e.goodsId === goods.id)
        if (!orderDetail) {
          orderDetail = {
            goodsId: goods.id,
            goodsBarcode: goods.barcode,
            goodsName: goods.name,
            quantity: 1,
            salePrice: goods.salePrice,
            salePoints: goods.salePoints,
            goodsPrice: goods.salePrice
          }
          this.orderList.push(orderDetail)
        } else {
          orderDetail.quantity += 1
        }
        this.barcode = ''
      } else {
        this.$notify({
          title: 'Warning',
          message: '未找到该商品',
          type: 'warning',
          duration: 2000
        })
      }
    },
    // 回车会员
    enterMember() {
      if (!this.member || (this.member && this.member.length < 1)) {
        this.isVip = false
        this.member = ''
        this.currentMember = null
        return
      }
      const member = this.members.find((e) => e.name === this.member)
      if (member) {
        this.currentMember = member
        this.isVip = true
      }
    },
    // 显示订单
    showOrder() {
      if (this.showOrderDialog === true) {
        if (this.fullscreenLoading === false) {
          this.settleAccounts()
        }
      } else {
        if (this.orderList.length === 0) {
          this.$notify({
            title: 'Warning',
            message: '没有商品',
            type: 'warning',
            duration: 2000
          })
        } else {
          this.showOrderDialog = true
        }
      }
    },
    // 结算
    settleAccounts() {
      this.fullscreenLoading = true
      const data = {
        member: this.currentMember?.id,
        details: this.orderList
      }
      posApi
        .settleAccounts(data)
        .then(() => {
          this.$notify({
            title: 'Success',
            message: '订单结算成功',
            type: 'success',
            duration: 2000
          })
          this.showOrderDialog = false
          this.settleAccountsOk()
          this.fullscreenLoading = false
        })
        .catch(() => {
          this.fullscreenLoading = false
        })
    },
    // 结算成功，清理打印
    settleAccountsOk() {
      this.barcode = null
      this.member = null
      this.currentMember = null
      this.isVip = false
      this.tool.editPrice = false
      this.clearOrderList()
      this.loadInit()
    },
    // ============================ 工具 ===============================
    // 刷新
    reload() {
      this.loadInit()
    },
    // 清空商品
    clearOrderList() {
      this.orderList = []
    },
    // 刷会员
    cancelVip() {
      this.isVip = !this.isVip
      if (!this.isVip) {
        this.member = null
        this.currentMember = null
        this.orderList.forEach((e) => {
          e.goodsPrice = e.salePrice
        })
      }
    },
    // 修改价格
    editPrice() {
      this.tool.editPrice = !this.tool.editPrice
      this.orderList.forEach((e) => {
        e.oldGoodsPrice = e.goodsPrice
      })
    },
    // 取消修改
    cancelEdit(goods) {
      goods.goodsPrice = goods.oldGoodsPrice
    },
    // 修改数量
    changeQuantity(goods) {
      // 为0删除商品
      if (goods.quantity === 0) {
        console.log(this.orderList)
        this.orderList = this.orderList.filter((e) => e.goodsId !== goods.goodsId)
      }
    }
  }
}
</script>

<style lang="scss">
.cashier {
  display: flex;
  flex-direction: column;
}

.orderDialog {
  .el-dialog {
    width: 100%;
    max-width: 450px;
  }
}

// 输入部分
.cashier-input {
  margin-bottom: 10px;
}

.cashier-input-item {
  width: 90%;
  padding: 7px;

  input {
    font-size: 14px;
    height: 42px !important;
    line-height: 42px !important;
  }

  li {
    display: flex;
    padding: 5px 20px;
    flex-direction: column;
    line-height: 20px;

    .label {
      font-size: 15px;
    }

    .desc {
      font-size: 13px;
      color: #999;
    }
  }
}

@media only screen and (max-width: 767px) {
  .cashier-input {
    .cashier-input-item {
      width: 100%;
    }
  }
}

// tool
.tool-card {
  .el-card__body {
    padding: 0.5rem !important;
  }
  .tool-list {
    display: flex;
    flex-direction: column;
    align-items: center;

    .el-button {
      width: 100px;
      height: 3rem;
      margin: 0.3rem;
    }
  }
}

@media only screen and (max-width: 767px) {
  .tool-card {
    .el-card__body {
      padding: 0.5rem !important;
    }
    .tool-list {
      display: flex;
      flex-direction: row;
      justify-content: space-around;
      flex-wrap: wrap;

      .el-button {
        width: 100px;
        height: 3rem;
        margin: 0.3rem;
      }
    }
  }
}

.el-autocomplete-suggestion li.highlighted,
.el-autocomplete-suggestion li:hover {
  background-color: lightgray;
}
</style>
