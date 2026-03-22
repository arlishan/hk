<template>
  <div class="app-shell">
    <TopBar title="我的" :user="profileUser" @toggle-theme="toggleTheme" />

    <main class="main-content">
      <LoadingBlock v-if="loading" text="正在加载用户信息..." />

      <ErrorBlock
        v-else-if="error"
        :text="error"
        @retry="loadProfile"
      />

      <template v-else>
        <section class="profile-card">
          <div class="profile-avatar">{{ profileUser?.avatar || 'L' }}</div>
          <h3>{{ profileUser?.name || '刘经理' }}</h3>
          <p>{{ profileUser?.role || '销售负责人 · 华东大区' }}</p>

          <div class="profile-badges">
            <span class="status-pill">本月目标达成 78%</span>
            <span class="status-pill">团队排名 Top 3</span>
          </div>
        </section>

        <section class="card">
          <div class="profile-menu">
            <button class="menu-item" @click="showPassword = true">
              修改密码
            </button>

            <button
              v-if="canRules"
              class="menu-item"
              @click="workflowType = 'segment'"
            >
              客户分层页
            </button>

            <button
              class="menu-item"
              @click="workflowType = 'archive'"
            >
              客户建档流程
            </button>

            <button
              v-if="canChannels"
              class="menu-item"
              @click="openChannelModal"
            >
              渠道投放设置
            </button>

            <button
              v-if="canRules"
              class="menu-item"
              @click="openRuleModal"
            >
              机器人规则编辑
            </button>

            <button
              v-if="canReports"
              class="menu-item"
              @click="$router.push('/reports')"
            >
              数据报表页
            </button>

            <button
              v-if="canTeam"
              class="menu-item"
              @click="$router.push('/team')"
            >
              团队成员页
            </button>

            <button class="menu-item danger-item" @click="handleLogout">
              退出登录
            </button>
          </div>
        </section>
      </template>
    </main>

    <BottomNav current="/profile" />

    <WorkflowModal
      v-if="workflowType"
      :type="workflowType"
      @close="workflowType = ''"
    />

    <ChannelModal
      v-if="showChannel && canChannels"
      :channels="channels"
      @close="showChannel = false"
      @edit="openChannelEdit"
      @reload="loadChannels"
    />

    <RuleModal
      v-if="showRule && canRules"
      :rules="rules"
      @close="showRule = false"
      @edit="showRuleEdit = true"
    />

    <EditChannelBudgetModal
      v-if="showChannelEdit && canChannels"
      :channel="editingChannel"
      @close="showChannelEdit = false"
      @save="saveChannelBudget"
    />

    <EditRuleFormModal
      v-if="showRuleEdit && canRules"
      :rule="rules"
      @close="showRuleEdit = false"
      @save="saveRules"
    />

    <ChangePasswordModal
      v-if="showPassword"
      @close="showPassword = false"
      @save="handleChangePassword"
    />

    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'

import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import WorkflowModal from '../components/WorkflowModal.vue'
import ChannelModal from '../components/ChannelModal.vue'
import RuleModal from '../components/RuleModal.vue'
import EditChannelBudgetModal from '../components/EditChannelBudgetModal.vue'
import EditRuleFormModal from '../components/EditRuleFormModal.vue'
import ChangePasswordModal from '../components/ChangePasswordModal.vue'
import ToastMessage from '../components/ToastMessage.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'

import {
  profileApi,
  fetchChannelsApi,
  updateChannelApi,
  fetchRulesApi,
  updateRulesApi,
  changePasswordApi
} from '../api'

import { getTheme, setTheme, logout } from '../store/app'
import { useToast } from '../composables/useToast'
import {
  canManageChannels,
  canManageRules,
  canViewReports,
  canViewTeam
} from '../utils/permission'

const router = useRouter()
const { toastText, toastVisible, showToast } = useToast()

const profileUser = ref(null)
const workflowType = ref('')
const showChannel = ref(false)
const showRule = ref(false)
const showChannelEdit = ref(false)
const showRuleEdit = ref(false)
const showPassword = ref(false)
const editingChannel = ref(null)

const channels = ref([])
const rules = ref({})

const loading = ref(false)
const error = ref('')

const canChannels = computed(() => canManageChannels(profileUser.value))
const canRules = computed(() => canManageRules(profileUser.value))
const canReports = computed(() => canViewReports(profileUser.value))
const canTeam = computed(() => canViewTeam(profileUser.value))

async function loadProfile() {
  loading.value = true
  error.value = ''
  try {
    profileUser.value = await profileApi()
  } catch (e) {
    error.value = e.message || '加载用户信息失败'
  } finally {
    loading.value = false
  }
}

async function loadChannels() {
  if (!canChannels.value) return
  channels.value = await fetchChannelsApi()
}

async function loadRules() {
  if (!canRules.value) return
  rules.value = await fetchRulesApi()
}

async function openChannelModal() {
  try {
    await loadChannels()
    showChannel.value = true
  } catch (e) {
    showToast(e.message || '加载渠道设置失败')
  }
}

async function openRuleModal() {
  try {
    await loadRules()
    showRule.value = true
  } catch (e) {
    showToast(e.message || '加载规则失败')
  }
}

function openChannelEdit(channel) {
  editingChannel.value = { ...channel }
  showChannelEdit.value = true
}

async function saveChannelBudget(payload) {
  try {
    await updateChannelApi({
      ...editingChannel.value,
      ...payload
    })
    await loadChannels()
    showChannelEdit.value = false
    showToast('渠道预算已更新')
  } catch (e) {
    showToast(e.message || '更新渠道预算失败')
  }
}

async function saveRules(payload) {
  try {
    rules.value = await updateRulesApi(payload)
    showRuleEdit.value = false
    showToast('机器人规则已更新')
  } catch (e) {
    showToast(e.message || '更新规则失败')
  }
}

async function handleChangePassword(payload) {
  try {
    await changePasswordApi(payload)
    showPassword.value = false
    showToast('密码修改成功，请重新登录')
    setTimeout(() => {
      logout()
      router.push('/login')
    }, 900)
  } catch (e) {
    showToast(e.message || '修改密码失败')
  }
}

function handleLogout() {
  logout()
  router.push('/login')
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

document.body.classList.toggle('dark', getTheme() === 'dark')
await loadProfile()
</script>
