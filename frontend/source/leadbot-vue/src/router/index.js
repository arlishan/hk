import { createRouter, createWebHashHistory } from 'vue-router'
import { isLogin, getUser } from '../store/app'
import { canViewReports, canViewTeam } from '../utils/permission'

import LoginView from '../views/LoginView.vue'
import DashboardView from '../views/DashboardView.vue'
import LeadsView from '../views/LeadsView.vue'
import ChatView from '../views/ChatView.vue'
import TasksView from '../views/TasksView.vue'
import ProfileView from '../views/ProfileView.vue'
import ReportsView from '../views/ReportsView.vue'
import TeamView from '../views/TeamView.vue'
import NoPermission from '../components/NoPermission.vue'

const routes = [
  { path: '/', redirect: '/dashboard' },
  { path: '/login', component: LoginView },
  { path: '/403', component: NoPermission },
  { path: '/dashboard', component: DashboardView },
  { path: '/leads', component: LeadsView },
  { path: '/chat', component: ChatView },
  { path: '/tasks', component: TasksView },
  { path: '/profile', component: ProfileView },
  { path: '/reports', component: ReportsView },
  { path: '/team', component: TeamView }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/login' && !isLogin()) {
    next('/login')
    return
  }

  if (to.path === '/login' && isLogin()) {
    next('/dashboard')
    return
  }

  const user = getUser()

  if (to.path === '/reports' && !canViewReports(user)) {
    next('/403')
    return
  }

  if (to.path === '/team' && !canViewTeam(user)) {
    next('/403')
    return
  }

  next()
})

export default router
