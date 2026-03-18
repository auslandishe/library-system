import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userId: localStorage.getItem('userId') || '',
    userName: localStorage.getItem('userName') || '',
  }),
  actions: {
    setLogin(data) {
      this.token = data.token
      this.userId = String(data.userId)
      this.userName = data.userName
      localStorage.setItem('token', data.token)
      localStorage.setItem('userId', String(data.userId))
      localStorage.setItem('userName', data.userName)
    },
    logout() {
      this.token = ''
      this.userId = ''
      this.userName = ''
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('userName')
    },
  },
})