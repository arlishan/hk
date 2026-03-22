<template>
  <div class="app-shell">
    <TopBar title="线索管理" :user="user" @toggle-theme="toggleTheme" />
    <main class="main-content">
      <section class="search-bar">
        <input
          v-model="keyword"
          type="text"
          placeholder="搜索客户姓名 / 公司 / 标签"
          @keydown.enter="loadLeads"
        />
      </section>

      <section class="filter-row">
        <button class="chip" :class="{ active: filter === 'all' }" @click="changeFilter('all')">全部</button>
        <button class="chip" :class="{ active: filter === 'high' }" @click="changeFilter('high')">高意向</button>
        <button class="chip" :class="{ active: filter === 'new' }" @click="changeFilter('new')">新线索</button>
        <button class="chip" :class="{ active: filter === 'follow' }" @click="changeFilter('follow')">待跟进</button>
      </section>

      <section class="card">
        <div class="section-head">
          <h3>客户线索列表</h3>
          <button class="primary-btn small" @click="showCreate = true">新建</button>
        </div>

        <div class="lead-summary-row">
          <span>共 {{ total }} 条</span>
          <div class="page-size-box">
            <label>每页</label>
            <select v-model="pageSize" @change="changePageSize">
              <option :value="5">5</option>
              <option :value="10">10</option>
              <option :value="20">20</option>
            </select>
          </div>
        </div>

        <LoadingBlock v-if="loading" text="正在加载线索数据..." />

        <ErrorBlock
          v-else-if="error"
          :text="error"
          @retry="loadLeads"
        />

        <template v-else>
          <div v-if="leads.length" class="lead-list">
            <div class="lead-card" v-for="item in leads" :key="item.id" @click="openLead(item)">
              <div class="lead-name-row">
                <h4>{{ item.name }}</h4>
                <span class="tag" :class="{ high: item.level === 'high', new: item.level === 'new' }">
                  {{ item.status }}
                </span>
              </div>
              <div class="lead-meta">
                <div>{{ item.company }} · {{ item.industry }}</div>
                <div>来源：{{ item.source }} · 最近跟进：{{ item.lastFollow }}</div>
              </div>
              <div class="lead-tags">
                <span class="tag" v-for="tag in item.tags" :key="tag">{{ tag }}</span>
              </div>
            </div>
          </div>

          <EmptyBlock
            v-else
            text="当前筛选条件下没有客户线索。"
          />

          <div class="pagination-bar" v-if="totalPages > 0">
            <button class="mini-btn" :disabled="page <= 1" @click="prevPage">上一页</button>
            <span>第 {{ page }} / {{ totalPages }} 页</span>
            <button class="mini-btn" :disabled="page >= totalPages" @click="nextPage">下一页</button>
          </div>
        </template>
      </section>
    </main>

    <BottomNav current="/leads" />

    <ModalShell v-if="showCreate" title="新建线索" @close="showCreate = false">
      <div class="form-group">
        <label>客户姓名</label>
        <input v-model="form.name" type="text" placeholder="请输入客户姓名" />
      </div>
      <div class="form-group">
        <label>公司名称</label>
        <input v-model="form.company" type="text" placeholder="请输入公司名称" />
      </div>
      <div class="form-group">
        <label>行业</label>
        <input v-model="form.industry" type="text" placeholder="请输入行业" />
      </div>
      <div class="form-group">
        <label>联系方式</label>
        <input v-model="form.phone" type="text" placeholder="请输入联系方式" />
      </div>
      <button class="primary-btn full" @click="handleCreate">保存线索</button>
    </ModalShell>

    <LeadDetailModal
      v-if="selectedLead"
      :lead="selectedLead"
      @close="selectedLead = null"
      @show-logs="openLogs"
      @show-archive="showArchive = true"
      @follow="handleFollow"
      @edit="canEditLead ? showEdit = true : null"
      @delete="canDelete ? handleDeleteLead : null"
      @add-log="showAddLog = true"
    />

    <TimelineModal
      v-if="showLogs"
      :logs="currentLogs"
      @close="showLogs = false"
    />

    <WorkflowModal
      v-if="showArchive"
      type="archive"
      @close="showArchive = false"
    />

    <EditLeadModal
      v-if="showEdit && canEditLead"
      :lead="selectedLead"
      @close="showEdit = false"
      @save="handleEditSave"
    />

    <AddLogModal
      v-if="showAddLog"
      @close="showAddLog = false"
      @save="handleAddLog"
    />

    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import ModalShell from '../components/ModalShell.vue'
import LeadDetailModal from '../components/LeadDetailModal.vue'
import TimelineModal from '../components/TimelineModal.vue'
import WorkflowModal from '../components/WorkflowModal.vue'
import EditLeadModal from '../components/EditLeadModal.vue'
import AddLogModal from '../components/AddLogModal.vue'
import ToastMessage from '../components/ToastMessage.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import {
  createLeadApi,
  fetchLeadsApi,
  fetchLogsApi,
  updateLeadApi,
  addLogApi,
  deleteLeadApi
} from '../api'
import { getTheme, setTheme, getUser } from '../store/app'
import { useToast } from '../composables/useToast'
import { canDeleteLead } from '../utils/permission'

const user = getUser()

const leads = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = ref(10)
const loading = ref(false)
const error = ref('')

const keyword = ref('')
const filter = ref('all')
const showCreate = ref(false)
const selectedLead = ref(null)
const showLogs = ref(false)
const showArchive = ref(false)
const showEdit = ref(false)
const showAddLog = ref(false)
const currentLogs = ref([])

const { toastText, toastVisible, showToast } = useToast()

const canDelete = computed(() => canDeleteLead(user))
const canEditLead = computed(() => true)

const form = reactive({
  name: '',
  company: '',
  industry: '',
  phone: ''
})

const totalPages = computed(() => {
  const pages = Math.ceil(total.value / pageSize.value)
  return pages > 0 ? pages : 1
})

async function loadLeads() {
  loading.value = true
  error.value = ''
  try {
    const res = await fetchLeadsApi({
      keyword: keyword.value,
      level: filter.value,
      page: page.value,
      pageSize: pageSize.value
    })

    leads.value = res.list || []
    total.value = res.total || 0
  } catch (e) {
    error.value = e.message || '加载线索失败'
    leads.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

function openLead(item) {
  selectedLead.value = item
}

async function openLogs(id) {
  try {
    currentLogs.value = await fetchLogsApi(id)
    showLogs.value = true
  } catch (e) {
    showToast(e.message || '加载跟进记录失败')
  }
}

async function handleFollow(id) {
  try {
    await updateLeadApi(id, {
      status: '已跟进',
      lastFollow: '刚刚'
    })

    await addLogApi(id, {
      time: '刚刚',
      title: '手动跟进',
      desc: '销售已完成本次客户跟进，并更新客户状态。'
    })

    await loadLeads()
    selectedLead.value = null
    showToast('客户已标记为已跟进')
  } catch (e) {
    showToast(e.message || '跟进失败')
  }
}

async function handleCreate() {
  if (!form.name || !form.company) {
    showToast('请至少填写姓名和公司')
    return
  }

  try {
    await createLeadApi(form)
    form.name = ''
    form.company = ''
    form.industry = ''
    form.phone = ''
    showCreate.value = false
    page.value = 1
    await loadLeads()
    showToast('线索已创建')
  } catch (e) {
    showToast(e.message || '创建线索失败')
  }
}

async function handleEditSave(payload) {
  if (!selectedLead.value) return
  try {
    await updateLeadApi(selectedLead.value.id, payload)
    showEdit.value = false
    selectedLead.value = null
    await loadLeads()
    showToast('客户信息已更新')
  } catch (e) {
    showToast(e.message || '更新客户失败')
  }
}

async function handleAddLog(payload) {
  if (!selectedLead.value) return
  try {
    await addLogApi(selectedLead.value.id, {
      time: '刚刚',
      title: payload.title || '新增记录',
      desc: payload.desc || ''
    })
    showAddLog.value = false
    currentLogs.value = await fetchLogsApi(selectedLead.value.id)
    showToast('跟进记录已新增')
  } catch (e) {
    showToast(e.message || '新增记录失败')
  }
}

async function handleDeleteLead(id) {
  if (!canDelete.value) {
    showToast('你没有删除客户权限')
    return
  }

  try {
    await deleteLeadApi(id)
    selectedLead.value = null

    if (page.value > 1 && leads.value.length === 1) {
      page.value -= 1
    }

    await loadLeads()
    showToast('客户已删除')
  } catch (e) {
    showToast(e.message || '删除客户失败')
  }
}

async function changeFilter(value) {
  filter.value = value
  page.value = 1
  await loadLeads()
}

async function changePageSize() {
  page.value = 1
  await loadLeads()
}

async function prevPage() {
  if (page.value <= 1) return
  page.value -= 1
  await loadLeads()
}

async function nextPage() {
  if (page.value >= totalPages.value) return
  page.value += 1
  await loadLeads()
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

document.body.classList.toggle('dark', getTheme() === 'dark')
await loadLeads()
</script>
