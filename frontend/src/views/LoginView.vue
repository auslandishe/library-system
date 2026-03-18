<template>
  <div class="page">
    <el-card class="card">
      <template #header>
        <div class="card-header">圖書借閱系統登入</div>
      </template>

      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="手機號碼" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="請輸入 09 開頭手機號碼" clearable />
        </el-form-item>

        <el-form-item label="密碼" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            show-password
            placeholder="請輸入密碼"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin">登入</el-button>
          <el-button @click="router.push('/register')">前往註冊</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginApi } from '@/api/auth'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  phoneNumber: '',
  password: '',
})

const rules = {
  phoneNumber: [
    { required: true, message: '請輸入手機號碼', trigger: 'blur' },
    { pattern: /^09\d{8}$/, message: '手機號碼格式錯誤', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '請輸入密碼', trigger: 'blur' },
  ],
}

const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    const res = await loginApi(form)
    authStore.setLogin(res.data)
    ElMessage.success('登入成功')
    router.push('/books')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fa;
}
.card {
  width: 440px;
}
.card-header {
  font-size: 18px;
  font-weight: 600;
  text-align: center;
}
</style>