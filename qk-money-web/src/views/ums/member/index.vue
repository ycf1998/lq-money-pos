<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <div v-if="crud.props.searchToggle" class="filter-container">
      <!-- 【搜索条件】prop前缀都要加query. -->
      <el-input v-model="query.name" placeholder="会员名称" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-input v-model="query.phone" placeholder="手机号码" class="filter-item-200" @keyup.enter.native="crud.toQuery" />
      <el-select v-model="query.type" clearable placeholder="会员类型" class="filter-item-200" @change="crud.toQuery">
        <el-option v-for="item in dict.memberType" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <rr-operation />
    </div>
    <!-- 操作栏 -->
    <crud-operation :permission="permission" :hidden-columns="[]" />
    <!-- 数据表格 -->
    <el-table ref="table" v-loading="crud.loading" :data="crud.data" style="width: 100%;" @selection-change="crud.selectionChangeHandler" @sort-change="crud.sortChangeHandler">
      <!-- 复选框-->
      <el-table-column type="selection" width="55" />

      <el-table-column prop="name" label="会员名称" />
      <el-table-column :show-overflow-tooltip="true" prop="type" label="会员类型">
        <template slot-scope="scope">
          {{ dict.label.memberType[scope.row.type] }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号码" />
      <el-table-column sortable prop="points" label="积分" />
      <el-table-column sortable prop="consumeAmount" label="总消费金额" />
      <el-table-column sortable prop="consumeTimes" label="消费次数" />
      <el-table-column sortable prop="createTime" label="创建时间" />
      <el-table-column sortable prop="updateTime" label="更新时间" />
      <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注" />

      <el-table-column label="操作" width="115" align="center" fixed="right">
        <template slot-scope="scope">
          <ud-operation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页栏 -->
    <pagination />
    <!-- 新增/修改表单 -->
    <el-dialog append-to-body :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="350px">
      <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
        <!-- 【表单控件】绑定的字段前缀都要加form. -->
        <el-form-item label="会员名称" prop="name">
          <el-input v-model="form.name" style="width: 220px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="会员类型" prop="type">
          <el-select v-model="form.type" style="width: 220px;" placeholder="会员类型">
            <el-option v-for="item in dict.memberType" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" style="width: 220px;" @keydown.native="keydown($event)" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model.trim="form.remark" style="width: 220px;" type="textarea" maxlength="250" show-word-limit />
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

import memberApi from '@/api/ums/member'
import { validatePhone, validNonnegative } from '@/utils/validate'

export default {
  name: 'Member',
  components: { Pagination, rrOperation, udOperation, crudOperation },
  cruds() {
    // 【url是查询接口】
    return CRUD({ title: '会员', url: '/umsMember', crudMethod: { ...memberApi } })
  },
  mixins: [presenter(), header(), form({
    // 【表单初始值】
    id: null,
    name: null,
    type: 'MEMBER',
    phone: null,
    points: 0.00,
    remark: null
  }), crud()],
  dicts: ['memberType'],
  data() {
    return {
      // 【操作权限定义】
      permission: {
        add: ['umsMember:add'],
        edit: ['umsMember:edit'],
        del: ['umsMember:del']
      },
      // 表单验证规则
      rules: {
        name: [
          { required: true, message: '请输入会员名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择会员类型', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { trigger: 'blur', validator: validatePhone }
        ],
        coupon: [
          { required: true, message: '请输入抵用券', trigger: 'blur' },
          { trigger: 'blur', validator: validNonnegative }
        ]
      }
    }
  },
  methods: {
    // 禁止输入空格
    keydown(e) {
      if (e.keyCode === 32) {
        e.returnValue = false
      }
    }
  }
}
</script>

