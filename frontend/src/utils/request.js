import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
})

request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  () =>
    Promise.reject({
      message: 'request error',
      status: 0,
    }),
)

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (!res.success) {
      const message = res.message || '操作失敗'
      ElMessage.error(message)
      return Promise.reject({
        message,
        status: response.status || 400,
      })
    }
    return res
  },
  (error) => {
    const message = error.response?.data?.message || '系統錯誤'
    const status = error.response?.status || 500

    ElMessage.error(message)

    if (status === 401 || message.includes('登入')) {
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('userName')
      if (window.location.pathname !== '/login') {
        window.location.href = '/login'
      }
    }

    return Promise.reject({
      message,
      status,
    })
  },
)

export default request