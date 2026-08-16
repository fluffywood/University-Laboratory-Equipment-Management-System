<template>
  <div class="equipment-register">
    <el-card>
      <div slot="header">
        <span>设备卡片登记</span>
      </div>
      
      <el-form
        ref="form"
        :model="formData"
        :rules="rules"
        label-width="120px"
        size="medium"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="使用单位号" prop="lydwh">
              <el-input v-model="formData.lydwh" placeholder="请输入使用单位号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用单位名" prop="lydwm">
              <el-input v-model="formData.lydwm" placeholder="请输入使用单位名"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备编号区间" prop="zcbhqj">
              <el-input v-model="formData.zcbhqj" placeholder="请输入设备编号区间"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类号" prop="zcflh">
              <el-input v-model="formData.zcflh" placeholder="请输入分类号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备名称" prop="zcmc">
              <el-input v-model="formData.zcmc" placeholder="请输入设备名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌型号" prop="ppxh">
              <el-input v-model="formData.ppxh" placeholder="请输入品牌型号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="规格" prop="gg">
              <el-input v-model="formData.gg" placeholder="请输入规格"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="sl">
              <el-input-number v-model="formData.sl" :min="1" placeholder="请输入数量"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单价" prop="dj">
              <el-input-number v-model="formData.dj" :min="0" :precision="2" placeholder="请输入单价"></el-input-number>
              <span style="margin-left: 8px;">元</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="金额" prop="je">
              <el-input-number v-model="formData.je" :min="0" :precision="2" placeholder="请输入金额" :disabled="true"></el-input-number>
              <span style="margin-left: 8px;">元</span>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="计量单位" prop="jldw">
              <el-select v-model="formData.jldw" placeholder="请选择计量单位">
                <el-option label="台" value="台"></el-option>
                <el-option label="套" value="套"></el-option>
                <el-option label="张" value="张"></el-option>
                <el-option label="个" value="个"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厂家" prop="cj">
              <el-input v-model="formData.cj" placeholder="请输入厂家"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="购置日期" prop="ggrq">
              <el-date-picker
                v-model="formData.ggrq"
                type="date"
                placeholder="选择购置日期"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现状" prop="xz">
              <el-select v-model="formData.xz" placeholder="请选择现状">
                <el-option label="在用" value="1"></el-option>
                <el-option label="闲置" value="2"></el-option>
                <el-option label="待修" value="3"></el-option>
                <el-option label="待报废" value="4"></el-option>
                <el-option label="丢失" value="5"></el-option>
                <el-option label="报废" value="6"></el-option>
                <el-option label="出售" value="7"></el-option>
                <el-option label="其它" value="9"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="经费科目" prop="jfkm">
              <el-select v-model="formData.jfkm" placeholder="请选择经费科目">
                <el-option label="教学" value="1"></el-option>
                <el-option label="科研" value="2"></el-option>
                <el-option label="基建" value="3"></el-option>
                <el-option label="自筹经费" value="4"></el-option>
                <el-option label="世界银行贷款" value="5"></el-option>
                <el-option label="捐赠" value="6"></el-option>
                <el-option label="其它" value="9"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票号" prop="fph">
              <el-input v-model="formData.fph" placeholder="请输入发票号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="供货商" prop="ghs">
              <el-input v-model="formData.ghs" placeholder="请输入供货商"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="存放地编号" prop="cfdbh">
              <el-input v-model="formData.cfdbh" placeholder="请输入存放地编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="存放地名称" prop="cfdmc">
              <el-input v-model="formData.cfdmc" placeholder="请输入存放地名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="使用人编号" prop="syrbh">
              <el-input v-model="formData.syrbh" placeholder="请输入使用人编号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="使用人" prop="syr">
              <el-input v-model="formData.syr" placeholder="请输入使用人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经手人" prop="jsr">
              <el-input v-model="formData.jsr" placeholder="请输入经手人"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="记帐人" prop="jzr">
              <el-input v-model="formData.jzr" placeholder="请输入记帐人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入账时间" prop="rzrq">
              <el-date-picker
                v-model="formData.rzrq"
                type="date"
                placeholder="选择入账时间"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="bz">
              <el-input
                v-model="formData.bz"
                type="textarea"
                :rows="3"
                placeholder="请输入备注信息"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item>
          <el-button type="primary" @click="submitForm" :loading="loading">提交审核</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'EquipmentRegister',
  data() {
    return {
      loading: false,
      formData: {
        lydwh: '',
        lydwm: '',
        zcbhqj: '',
        zcflh: '',
        zcmc: '',
        ppxh: '',
        gg: '',
        sl: 1,
        dj: null,
        je: null,
        jldw: '',
        cj: '',
        ggrq: '',
        xz: '',
        jfkm: '',
        fph: '',
        ghs: '',
        cfdbh: '',
        cfdmc: '',
        syrbh: '',
        syr: '',
        jsr: '',
        jzr: '',
        rzrq: '',
        bz: '',
        srr: 'admin',
        srrq: new Date()
      },
      rules: {
        lydwh: [
          { required: true, message: '请输入使用单位号', trigger: 'blur' }
        ],
        zcflh: [
          { required: true, message: '请输入分类号', trigger: 'blur' }
        ],
        zcmc: [
          { required: true, message: '请输入设备名称', trigger: 'blur' }
        ],
        ppxh: [
          { required: true, message: '请输入品牌型号', trigger: 'blur' }
        ],
        gg: [
          { required: true, message: '请输入规格', trigger: 'blur' }
        ],
        je: [
          { required: true, message: '请输入金额', trigger: 'blur' }
        ],
        cj: [
          { required: true, message: '请输入厂家', trigger: 'blur' }
        ],
        ggrq: [
          { required: true, message: '请选择购置日期', trigger: 'change' }
        ],
        xz: [
          { required: true, message: '请选择现状', trigger: 'change' }
        ],
        jfkm: [
          { required: true, message: '请选择经费科目', trigger: 'change' }
        ],
        syr: [
          { required: true, message: '请输入使用人', trigger: 'blur' }
        ],
        jsr: [
          { required: true, message: '请输入经手人', trigger: 'blur' }
        ],
        jzr: [
          { required: true, message: '请输入记帐人', trigger: 'blur' }
        ],
        rzrq: [
          { required: true, message: '请选择入账时间', trigger: 'change' }
        ]
      }
    }
  },
  methods: {
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        console.log(this.formData)
        if (valid) {
          this.loading = true
          try {
            const response = await request({
              url: '/equipment/submit',
              method: 'post',
              data: this.formData
            })
            
            this.$message.success(response.message)
            this.resetForm()
          } catch (error) {
            console.error('提交失败:', error)
          } finally {
            this.loading = false
          }
        }
      })
    },
    resetForm() {
      this.$refs.form.resetFields()
      this.formData = {
        lydwh: '',
        lydwm: '',
        zcbhqj: '',
        zcflh: '',
        zcmc: '',
        ppxh: '',
        gg: '',
        sl: 1,
        dj: null,
        je: null,
        jldw: '',
        cj: '',
        ggrq: '',
        xz: '',
        jfkm: '',
        fph: '',
        ghs: '',
        cfdbh: '',
        cfdmc: '',
        syrbh: '',
        syr: '',
        jsr: '',
        jzr: '',
        rzrq: '',
        bz: '',
        srr: 'admin',
        srrq: new Date()
      }
    },
    calcAmount() {
      if (this.formData.dj != null && this.formData.sl != null && this.formData.sl > 0) {
        this.formData.je = Number(this.formData.dj) * Number(this.formData.sl)
      } else {
        this.formData.je = null
      }
    }
  },
  watch: {
    'formData.dj': 'calcAmount',
    'formData.sl': 'calcAmount'
  }
}
</script>

<style lang="scss" scoped>
.equipment-register {
  .el-card {
    margin-bottom: 20px;
  }
  
  .el-form-item {
    margin-bottom: 20px;
  }
}
</style> 