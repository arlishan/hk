<template>
  <div class="login-screen">
    <div class="login-card">
      <div class="login-logo">AI</div>
      <h1>获客机器人</h1>
      <p>登录后进入你的智能获客工作台</p>

      <div class="form-group">
        <label>账号</label>
        <input
          v-model.trim="username"
          type="text"
          placeholder="请输入账号，如：admin"
          autocomplete="username"
        />
      </div>

      <div class="form-group">
        <label>密码</label>
        <input
          v-model.trim="password"
          type="password"
          placeholder="请输入密码，如：123456"
          autocomplete="current-password"
          @keydown.enter="handleLogin"
        />
      </div>

      <div v-if="errorText" class="form-error">
        {{ errorText }}
      </div>

      <button class="primary-btn full" :disabled="loading" @click="handleLogin">
        {{ loading ? '登录中...' : '登录' }}
      </button>

      <div class="login-tips">
        <p>演示账号：admin</p>
        <p>演示密码：123456</p>
        <p>经理账号：manager / 123456</p>
        <p>普通账号：user / 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { loginApi } from '../api'
import { setToken, setUser, getTheme } from '../store/app'

const router = useRouter()

const username = ref('admin')
const password = ref('123456')
const loading = ref(false)
const errorText = ref('')

document.body.classList.toggle('dark', getTheme() === 'dark')

async function handleLogin() {
  errorText.value = ''

  if (!username.value || !password.value) {
    errorText.value = '请输入账号和密码'
    return
  }

  loading.value = true
  try {
    const res = await loginApi({
      username: username.value,
      password: password.value
    })

    setToken(res.token)
    setUser(res.user)

    router.push('/dashboard')
  } catch (error) {
    errorText.value = error?.message || '登录失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.form-error {
  margin: 4px 0 12px;
  color: #ef4444;
  font-size: 13px;
  line-height: 1.5;
}

.primary-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style>
