<template>
  <div class="app-shell">
    <TopBar title="任务中心" :user="user" @toggle-theme="toggleTheme" />

    <main class="main-content">
      <section class="card">
        <div class="section-head">
          <h3>任务看板</h3>
          <button class="primary-btn small" @click="reloadTasks">刷新</button>
        </div>

        <LoadingBlock v-if="loading" text="正在加载任务数据..." />

        <ErrorBlock
          v-else-if="error"
          :text="error"
          @retry="loadTasks"
        />

        <template v-else>
          <div v-if="tasks.length" class="task-list">
            <div
              class="task-item"
              :class="{ done: task.done }"
              v-for="task in tasks"
              :key="task.id"
            >
              <input
                class="task-check"
                type="checkbox"
                :checked="task.done"
                @change="toggle(task)"
              />
              <div class="task-content">
                <h4>{{ task.title }}</h4>
                <p>{{ task.desc }}</p>
              </div>
            </div>
          </div>

          <EmptyBlock
            v-else
            text="当前没有待处理任务。"
          />
        </template>
      </section>
    </main>

    <BottomNav current="/tasks" />
    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import ToastMessage from '../components/ToastMessage.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import { fetchTasksApi, updateTaskApi } from '../api/mock'
import { getTheme, setTheme, getUser } from '../store/app'
import { useToast } from '../composables/useToast'

const user = getUser()
const tasks = ref([])
const loading = ref(false)
const error = ref('')

const { toastText, toastVisible, showToast } = useToast()

async function loadTasks() {
  loading.value = true
  error.value = ''

  try {
    tasks.value = await fetchTasksApi()
  } catch (e) {
    error.value = e.message || '加载任务失败'
    tasks.value = []
  } finally {
    loading.value = false
  }
}

async function toggle(task) {
  try {
    await updateTaskApi(task.id, !task.done)
    await loadTasks()
    showToast(task.done ? '任务已恢复' : '任务已完成')
  } catch (e) {
    showToast(e.message || '更新任务失败')
  }
}

async function reloadTasks() {
  await loadTasks()
  showToast('任务数据已刷新')
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

document.body.classList.toggle('dark', getTheme() === 'dark')
await loadTasks()
</script>

<style scoped>
.task-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.task-item {
  display: flex;
  gap: 12px;
  align-items: flex-start;
  padding: 14px;
  border-radius: 18px;
  background: var(--card-soft);
  border: 1px solid var(--line);
  transition: 0.2s ease;
}

.task-item.done {
  opacity: 0.6;
}

.task-check {
  width: 20px;
  height: 20px;
  margin-top: 2px;
  flex-shrink: 0;
}

.task-content {
  flex: 1;
}

.task-content h4 {
  margin: 0 0 4px;
  font-size: 15px;
}

.task-content p {
  margin: 0;
  color: var(--subtext);
  font-size: 13px;
  line-height: 1.6;
}
</style>
