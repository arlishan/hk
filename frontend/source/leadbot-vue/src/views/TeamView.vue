<template>
  <div class="app-shell">
    <TopBar title="团队成员" :user="user" @toggle-theme="toggleTheme" />

    <main class="main-content" v-if="allow">
      <section class="card">
        <div class="section-head">
          <h3>成员列表</h3>
          <button class="primary-btn small" @click="openCreate">新增成员</button>
        </div>

        <LoadingBlock v-if="loading" text="正在加载团队成员..." />

        <ErrorBlock
          v-else-if="error"
          :text="error"
          @retry="loadMembers"
        />

        <template v-else>
          <div v-if="members.length" class="team-list">
            <div class="team-item" v-for="item in members" :key="item.id">
              <div class="team-avatar">{{ item.avatar }}</div>

              <div class="team-info">
                <strong>{{ item.name }}</strong>
                <p>{{ item.role }}</p>
              </div>

              <div class="team-metric">
                <span>本周线索</span>
                <strong>{{ item.leads }}</strong>
              </div>

              <div class="team-actions">
                <button class="mini-btn" @click="openEdit(item)">编辑</button>
                <button class="mini-btn danger" @click="removeMember(item.id)">删除</button>
              </div>
            </div>
          </div>

          <EmptyBlock
            v-else
            text="当前还没有团队成员数据。"
          />
        </template>
      </section>
    </main>

    <main class="main-content" v-else>
      <NoPermission />
    </main>

    <BottomNav current="/team" />

    <EditMemberModal
      v-if="showEdit && allow"
      :member="editingMember"
      @close="showEdit = false"
      @save="saveMember"
    />

    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import EditMemberModal from '../components/EditMemberModal.vue'
import ToastMessage from '../components/ToastMessage.vue'
import NoPermission from '../components/NoPermission.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import { fetchMembersApi, saveMemberApi, deleteMemberApi } from '../api/mock'
import { getTheme, setTheme, getUser } from '../store/app'
import { useToast } from '../composables/useToast'
import { canManageMembers } from '../utils/permission'

const user = getUser()
const allow = computed(() => canManageMembers(user))
const members = ref([])
const loading = ref(false)
const error = ref('')
const showEdit = ref(false)
const editingMember = ref(null)
const { toastText, toastVisible, showToast } = useToast()

async function loadMembers() {
  if (!allow.value) return
  loading.value = true
  error.value = ''
  try {
    members.value = await fetchMembersApi()
  } catch (e) {
    error.value = e.message || '加载团队成员失败'
    members.value = []
  } finally {
    loading.value = false
  }
}

function openCreate() {
  editingMember.value = {
    name: '',
    role: '',
    leads: 0,
    avatar: 'N'
  }
  showEdit.value = true
}

function openEdit(item) {
  editingMember.value = { ...item }
  showEdit.value = true
}

async function saveMember(payload) {
  try {
    await saveMemberApi({
      ...editingMember.value,
      ...payload,
      leads: Number(payload.leads || 0)
    })
    await loadMembers()
    showEdit.value = false
    showToast('成员信息已保存')
  } catch (e) {
    showToast(e.message || '保存成员失败')
  }
}

async function removeMember(id) {
  try {
    await deleteMemberApi(id)
    await loadMembers()
    showToast('成员已删除')
  } catch (e) {
    showToast(e.message || '删除成员失败')
  }
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

await loadMembers()
</script>
