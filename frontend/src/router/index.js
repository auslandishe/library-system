import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import BookListView from '@/views/BookListView.vue'
import MyBorrowView from '@/views/MyBorrowView.vue'
import MainLayout from '@/layouts/MainLayout.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'login', component: LoginView },
    { path: '/register', name: 'register', component: RegisterView },
    {
      path: '/',
      component: MainLayout,
      children: [
        { path: '/books', name: 'books', component: BookListView, meta: { requiresAuth: true } },
        { path: '/my-borrows', name: 'my-borrows', component: MyBorrowView, meta: { requiresAuth: true } },
      ],
    },
  ],
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
    return
  }
  if ((to.path === '/login' || to.path === '/register') && token) {
    next('/books')
    return
  }
  next()
})

export default router