<template>
  <div class="app-container">
    <!-- 搜索 -->
    <div v-if="crud.props.searchToggle" class="filter-container">
      <!-- 【搜索条件】prop前缀都要加query. -->
      <el-input v-model="query.barcode" placeholder="条码" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-input v-model="query.name" placeholder="名称" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-select v-model="query.status" clearable placeholder="状态" class="filter-item-200" @change="crud.toQuery">
        <el-option v-for="item in dict.goodsStatus" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <rr-operation />
    </div>
    <!-- CRUD操作 -->
    <crud-operation :permission="permission" :hidden-columns="['createTime', 'updateTime', 'description']" />
    <!-- 列表 -->
    <el-table ref="table" v-loading="crud.loading" :data="crud.data" style="width: 100%;" @selection-change="crud.selectionChangeHandler" @sort-change="crud.sortChangeHandler">
      <el-table-column type="selection" width="55" />
      <!-- 【列控件】增加sortable支持排序 -->
      <el-table-column align="center" prop="pic" label="图片">
        <template slot-scope="scope">
          <el-image style="width: 30px; height: 30px" :src="loadPic(scope.row.pic)" :preview-src-list="[loadPic(scope.row.pic)]" />
        </template>
      </el-table-column>
      <el-table-column align="center" prop="barcode" label="条码" />
      <el-table-column align="center" prop="name" label="名称" />
      <el-table-column align="center" prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag :type="badgeType(scope.row.status)">{{ dict.label.goodsStatus[scope.row.status] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="salePrice" label="售价" />
      <el-table-column align="center" prop="salePoints" label="消耗积分" />
      <el-table-column align="center" prop="purchasePrice" label="进价" />
      <el-table-column align="center" prop="stock" label="库存" />
      <el-table-column align="center" sortable prop="sales" label="销量" />
      <el-table-column align="center" prop="unit" label="单位" />
      <el-table-column align="center" prop="size" label="规格" />
      <el-table-column align="center" prop="createTime" label="创建时间" />
      <el-table-column align="center" prop="updateTime" label="更新时间" />
      <el-table-column align="center" prop="description" label="描述" />
      <el-table-column label="操作" width="115" align="center" fixed="right">
        <template slot-scope="scope">
          <ud-operation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination />
    <!--表单-->
    <el-dialog append-to-body :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="600px">
      <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
        <!-- 【表单控件】绑定的字段前缀都要加form. -->
        <el-form-item label="图片" prop="pic">
          <el-upload class="avatar-uploader" action="" :auto-upload="false" :on-change="handlePicSuccess" :show-file-list="false" accept="image/*">
            <img v-if="form.pic" :src="loadPic(form.pic)" style="width: 100px; height: 100px">
            <i v-else class="el-icon-plus" />
          </el-upload>
        </el-form-item>
        <br>
        <el-form-item label="条码" prop="barcode">
          <el-input v-model="form.barcode" style="width: 450px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="消耗积分" prop="salePoints">
          <el-input v-model="form.salePoints" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="进价" prop="purchasePrice">
          <el-input v-model="form.purchasePrice" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="售价" prop="salePrice">
          <el-input v-model="form.salePrice" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" style="width: 178px;" placeholder="请选择">
            <el-option v-for="item in dict.goodsStatus" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <Compute-Input v-model="form.stock" style="width: 178px;" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="规格" prop="size">
          <el-input v-model="form.size" style="width: 178px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model.trim="form.description" style="width: 450px;" type="textarea" maxlength="250" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import rrOperation from '@/components/Crud/RR.operation.vue'
import udOperation from '@/components/Crud/UD.operation.vue'
import crudOperation from '@/components/Crud/CRUD.operation.vue'
import Pagination from '@/components/Crud/Pagination.vue'
import CRUD, { presenter, header, form, crud } from '@/components/Crud/crud'
// 【引入对应的api文件】基础crud, 方法名必须为 add,edit,del
import crudGoods from '@/api/gms/goods'

import ComputeInput from '@/components/ComputeInput'
import oss from '@/utils/oss'
import { validNonnegative } from '@/utils/validate'

export default {
  name: 'Member',
  components: { Pagination, rrOperation, udOperation, crudOperation, ComputeInput },
  cruds() {
    // 【url是查询接口】
    return CRUD({ title: '商品', url: '/gmsGoods', crudMethod: { ...crudGoods } })
  },
  mixins: [presenter(), header(), form({
    // 【表单初始值】
    id: null,
    pic: null,
    picFile: null,
    status: 'SALE',
    brandId: 0,
    barcode: null,
    name: null,
    purchasePrice: null,
    salePrice: null,
    salePoints: 0,
    stock: 0,
    unit: null,
    size: null,
    description: null
  }), crud()],
  dicts: ['goodsStatus'],
  data() {
    return {
      // 【操作权限定义】
      permission: {
        add: ['gmsGoods:add'],
        edit: ['gmsGoods:edit'],
        del: ['gmsGoods:del']
      },
      // 表单验证规则
      rules: {
        barcode: [
          { required: true, message: '请输入条码', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        purchasePrice: [
          { required: true, message: '请输入进价', trigger: 'blur' },
          { trigger: 'blur', validator: validNonnegative }
        ],
        salePrice: [
          { required: true, message: '请输入售价', trigger: 'blur' },
          { trigger: 'blur', validator: validNonnegative }
        ],
        salePoints: [
          { required: true, message: '请输入积分', trigger: 'blur' },
          { trigger: 'blur', validator: validNonnegative }
        ],
        stock: [
          { required: true, message: '请输入库存', trigger: 'blur' },
          { trigger: 'blur', validator: validNonnegative }
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 标记type
    badgeType(status) {
      switch (status) {
        case 'SOLD_OUT': return 'warning'
        case 'UN_SHELVE': return 'info'
        default: return 'primary'
      }
    },
    // 加载pic
    loadPic(url) {
      return oss.loadImage(url, oss.TYPE.LOCAL)
    },
    // 选择pic预览
    handlePicSuccess(file) {
      this.form.pic = URL.createObjectURL(file.raw)
      this.form.picFile = file.raw
    },
    // 禁止输入空格
    keydown(e) {
      if (e.keyCode === 32) {
        e.returnValue = false
      }
    }
  }
}
</script>
<style>
.cell {
  overflow: inherit !important;
}
</style>
