<template>
  <div class="container">
    <div class="toolbar">
      <h2>我的借閱</h2>
      <div>
        <el-button @click="router.push('/books')">返回書籍列表</el-button>
      </div>
    </div>

    <el-table v-loading="loading" :data="tableData" border>
      <el-table-column prop="inventoryId" label="庫存ID" width="100" />
      <el-table-column prop="isbn" label="ISBN" width="150" />
      <el-table-column prop="bookName" label="書名" min-width="180" />
      <el-table-column prop="author" label="作者" width="150" />
      <el-table-column prop="borrowingTime" label="借閱時間" width="180" />
      <el-table-column prop="location" label="館藏位置" width="120" />
      <el-table-column prop="status" label="狀態" width="120">
        <template #default="scope">
          <el-tag type="warning">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button type="warning" size="small" @click="handleReturn(scope.row.inventoryId)">
            還書
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { myBorrowListApi } from '@/api/book'
import { returnApi } from '@/api/borrow'

const router = useRouter()
const tableData = ref([])
const loading = ref(false)

const loadData = async () => {
  loading.value = true
  try {
    const res = await myBorrowListApi()
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

const handleReturn = async (inventoryId) => {
  await ElMessageBox.confirm('確定要歸還這本書嗎？', '提示', { type: 'warning' })
  await returnApi({ inventoryId })
  ElMessage.success('還書成功')
  await loadData()
}

onMounted(loadData)
</script>

<style scoped>
.container {
  padding: 24px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
</style>