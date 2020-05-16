<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table
      :data="cards"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="selsChange"
      style="width: 100%;"
    >
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column prop="id" label="编号" width="120" sortable></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120" sortable></el-table-column>
      <el-table-column prop="imgUrl" label="头像" width="100" sortable></el-table-column>
      <el-table-column prop="username" label="姓名" width="100" sortable></el-table-column>
      <el-table-column prop="phone" label="手机" width="120" sortable></el-table-column>
      <el-table-column
        prop="cardType"
        label="年卡类型"
        :formatter="formatType"
        min-width="180"
        sortable
      ></el-table-column>
      <el-table-column
        prop="validDate"
        label="有效期"
        min-width="180"
        :formatter="formatDate"
        sortable
      ></el-table-column>
      <el-table-column prop="cardNo" label="卡号" min-width="180" sortable></el-table-column>

      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="编号" prop="id">
          <el-input v-model="editForm.id" disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="editForm.nickname" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="editForm.imgUrl" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="editForm.username" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="editForm.phone" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="年卡类型">
          <el-radio-group v-model="editForm.cardType">
            <el-radio class="radio" :label="1">199</el-radio>
            <el-radio class="radio" :label="0">99</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.validDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="卡号">
          <el-input v-model="editForm.cardNo" :min="0" :max="200"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="昵称">
          <el-input v-model="addForm.nickname" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="addForm.imgUrl" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="addForm.username" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="addForm.phone" :min="0" :max="200"></el-input>
        </el-form-item>
        <el-form-item label="年卡类型">
          <el-radio-group v-model="addForm.cardType">
            <el-radio class="radio" :label="1">亲子年卡（两大一小）138元</el-radio>
            <el-radio class="radio" :label="0">单人年卡99元</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="有效期">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.validDate"></el-date-picker>
        </el-form-item>
        <el-form-item label="卡号">
          <el-input v-model="addForm.cardNo" :min="0" :max="200"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
//import NProgress from 'nprogress'
import { listMemberCards, addMemberCard, updateMemberCard, deleteMemberCard } from '../../api/api';

export default {
  data () {
    return {
      filters: {
        name: ''
      },
      cards: [],
      total: 0,
      page: 1,
      pageSize: 20,

      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
      },
      //编辑界面数据
      editForm: {
        id: 0,
        nickname: '',
        imgUrl: '',
        username: '',
        phone: '',
        cardType: '',
        validDate: '',
        cardNo: ''
      },

      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      addFormRules: {
      },
      //新增界面数据
      addForm: {
        nickname: '',
        imgUrl: '',
        username: '',
        phone: '',
        cardType: '',
        validDate: '',
        cardNo: ''
      }

    }
  },
  methods: {
    //性别显示转换
    formatType: function (row, column) {
      return row.cardType == 1 ? '亲子年卡（两大一小）138元' : row.cardType == 0 ? '单人年卡99元' : '未知';
    },

    formatDate: function (row, column) {
      //return row.validDate.substring(0, 10);
      return row.validDate;
    },

    handleCurrentChange (val) {
      this.page = val;
      this.getMemeberCards();
    },

    //获取用户列表
    getMemeberCards () {
      let para = {
        pageNo: this.page,
        pageSize: this.pageSize,
      };
      this.listLoading = true;
      //NProgress.start();
      listMemberCards(para).then((res) => {
        this.total = res.data.total;
        this.cards = res.data.data;
        this.listLoading = false;
        //NProgress.done();
      });
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = { id: row.id };
        deleteMemberCard(para).then((res) => {
          this.listLoading = false;
          //NProgress.done();
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getMemeberCards();
        });
      }).catch(() => {

      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //显示新增界面
    handleAdd: function () {
      this.addFormVisible = true;
      this.addForm = {
        nickname: '',
        imgUrl: '',
        username: '',
        phone: '',
        cardType: '',
        validDate: '',
        cardNo: ''
      };
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editForm);
            updateMemberCard(para).then((res) => {
              this.editLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.$refs['editForm'].resetFields();
              this.editFormVisible = false;
              this.getMemeberCards();
            });
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.addForm);
            addMemberCard(para).then((res) => {
              this.addLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.$refs['addForm'].resetFields();
              this.addFormVisible = false;
              this.getMemeberCards();
            });
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
  },
  mounted () {
    this.getMemeberCards();
  }
}

</script>

<style scoped>
</style>