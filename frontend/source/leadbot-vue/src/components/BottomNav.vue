<template>
  <nav class="bottom-nav">
    <button
      class="nav-item"
      :class="{ active: current === '/dashboard' }"
      @click="$router.push('/dashboard')"
    >
      <span>⌂</span>
      <small>工作台</small>
    </button>

    <button
      class="nav-item"
      :class="{ active: current === '/leads' }"
      @click="$router.push('/leads')"
    >
      <span>◎</span>
      <small>线索</small>
    </button>

    <button
      class="nav-item"
      :class="{ active: current === '/chat' }"
      @click="$router.push('/chat')"
    >
      <span>✦</span>
      <small>AI</small>
    </button>

    <button
      class="nav-item"
      :class="{ active: current === '/tasks' }"
      @click="$router.push('/tasks')"
    >
      <span>✓</span>
      <small>任务</small>
    </button>

    <button
      class="nav-item"
      :class="{ active: isProfileActive }"
      @click="$router.push('/profile')"
    >
      <span>◉</span>
      <small>我的</small>
    </button>
  </nav>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  current: {
    type: String,
    default: '/dashboard'
  }
})

const isProfileActive = computed(() => {
  return ['/profile', '/reports', '/team'].includes(props.current)
})
</script>
