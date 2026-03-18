<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>書籍列表</h2>
        <p>可在此查看館藏與進行借閱</p>
      </div>
    </div>

    <el-table v-loading="loading" :data="tableData" border stripe class="table-card">
      <el-table-column prop="inventoryId" label="庫存ID" width="100" />
      <el-table-column prop="isbn" label="ISBN" width="150" />
      <el-table-column prop="bookName" label="書名" min-width="180" />
      <el-table-column prop="author" label="作者" width="150" />
      <el-table-column prop="location" label="館藏位置" width="120" />
      <el-table-column prop="status" label="狀態" width="120">
        <template #default="scope">
          <el-tag :type="statusTagType(scope.row.status)">
            {{ statusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            :disabled="scope.row.status !== 'AVAILABLE'"
            @click="handleBorrow(scope.row.inventoryId)"
          >
            借閱
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listBooksApi } from '@/api/book'
import { borrowApi } from '@/api/borrow'

const tableData = ref([])
const loading = ref(false)

const statusText = (status) => {
  if (status === 'AVAILABLE') return '在庫'
  if (status === 'BORROWED') return '出借中'
  if (status === 'SORTING') return '整理中'
  if (status === 'LOST') return '遺失'
  if (status === 'DAMAGED') return '損毀'
  if (status === 'DISCARDED') return '廢棄'
  return status
}

const statusTagType = (status) => {
  if (status === 'AVAILABLE') return 'success'
  if (status === 'BORROWED') return 'warning'
  if (status === 'SORTING') return 'info'
  if (status === 'LOST') return 'danger'
  if (status === 'DAMAGED') return 'danger'
  if (status === 'DISCARDED') return ''
  return 'info'
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await listBooksApi()
    tableData.value = res.data
  } finally {
    loading.value = false
  }
}

const handleBorrow = async (inventoryId) => {
  await ElMessageBox.confirm('確定要借閱這本書嗎？', '提示', { type: 'warning' })
  await borrowApi({ inventoryId })
  ElMessage.success('借書成功')
  await loadData()
}

onMounted(loadData)
</script>

<style scoped>
.page-card {
  background: #ffffff;
  border-radius: 18px;
  padding: 24px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  font-size: 24px;
  color: #111827;
}

.page-header p {
  margin: 6px 0 0;
  color: #6b7280;
  font-size: 14px;
}

.table-card {
  border-radius: 12px;
  overflow: hidden;
}
</style>