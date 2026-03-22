<template>
  <div class="app-shell">
    <TopBar title="工作台" :user="user" @toggle-theme="toggleTheme" />
    <main class="main-content">
      <LoadingBlock v-if="loading" text="正在加载工作台数据..." />

      <ErrorBlock
        v-else-if="error"
        :text="error"
        @retry="loadDashboard"
      />

      <template v-else>
        <section class="hero-panel">
          <div class="hero-left">
            <span class="status-pill online">机器人运行中</span>
            <h2>AI 正在为你自动发现潜在客户</h2>
            <p>
              今日已完成 324 次智能触达，筛选出 26 条高质量线索，
              建议优先处理高意向客户。
            </p>
            <div class="hero-actions">
              <button class="primary-btn" @click="$router.push('/chat')">
                进入 AI 助手
              </button>
            </div>
          </div>

          <div class="hero-right">
            <div class="hero-score">
              <span>今日线索得分</span>
              <strong>89</strong>
              <em>表现优秀</em>
            </div>
          </div>
        </section>

        <section class="card">
          <div class="section-head">
            <h3>快捷功能</h3>
          </div>

          <div class="feature-grid">
            <button class="feature-box" @click="$router.push('/leads')">
              <div class="feature-icon blue">◎</div>
              <div>
                <strong>线索池</strong>
                <p>查看全部客户线索</p>
              </div>
            </button>

            <button class="feature-box" @click="showArchive = true">
              <div class="feature-icon orange">▣</div>
              <div>
                <strong>客户建档</strong>
                <p>完善客户档案信息</p>
              </div>
            </button>

            <button
              v-if="canRules"
              class="feature-box"
              @click="showSegment = true"
            >
              <div class="feature-icon green">A</div>
              <div>
                <strong>客户分层</strong>
                <p>查看 A/B/C 客户分类</p>
              </div>
            </button>

            <button
              v-if="canRules"
              class="feature-box"
              @click="showStrategy = true"
            >
              <div class="feature-icon purple">✦</div>
              <div>
                <strong>策略配置</strong>
                <p>查看机器人策略</p>
              </div>
            </button>
          </div>
        </section>

        <section class="stats-grid">
          <div class="stat-card">
            <span>新增线索</span>
            <strong>{{ newCount + 22 }}</strong>
            <em class="up">+8.3%</em>
          </div>

          <div class="stat-card">
            <span>高意向客户</span>
            <strong>{{ 100 + highCount }}</strong>
            <em class="up">转化率 18.6%</em>
          </div>

          <div class="stat-card">
            <span>待跟进客户</span>
            <strong>{{ 30 + followCount }}</strong>
            <em class="warn">需尽快处理</em>
          </div>

          <div class="stat-card">
            <span>AI 对话次数</span>
            <strong>{{ 300 + chats.length }}</strong>
            <em class="up">自动推进中</em>
          </div>
        </section>

        <section v-if="hotLeads.length" class="card">
          <div class="section-head">
            <h3>本周线索趋势</h3>
          </div>
          <canvas
            ref="trendRef"
            class="chart-canvas"
            width="360"
            height="180"
          ></canvas>
        </section>

        <section v-if="hotLeads.length" class="double-grid">
          <div class="card">
            <div class="section-head">
              <h3>客户来源分析</h3>
            </div>
            <canvas
              ref="sourceRef"
              class="chart-canvas small"
              width="160"
              height="160"
            ></canvas>
          </div>

          <div class="card">
            <div class="section-head">
              <h3>转化漏斗</h3>
            </div>
            <canvas
              ref="funnelRef"
              class="chart-canvas small"
              width="160"
              height="160"
            ></canvas>
          </div>
        </section>

        <section class="card">
          <div class="section-head">
            <h3>最近高意向客户</h3>
            <button class="text-btn" @click="$router.push('/leads')">
              查看全部
            </button>
          </div>

          <EmptyBlock
            v-if="!hotLeads.length"
            text="当前还没有高意向客户。"
          />

          <div v-else class="list">
            <div class="list-item" v-for="item in hotLeads" :key="item.id">
              <div class="list-item-top">
                <div>
                  <h4>{{ item.name }} · {{ item.company }}</h4>
                  <p>{{ item.industry }} · 最近跟进：{{ item.lastFollow }}</p>
                </div>
                <span class="tag high">高意向</span>
              </div>
            </div>
          </div>
        </section>
      </template>
    </main>

    <BottomNav current="/dashboard" />

    <WorkflowModal
      v-if="showArchive"
      type="archive"
      @close="showArchive = false"
    />

    <WorkflowModal
      v-if="showSegment"
      type="segment"
      @close="showSegment = false"
    />

    <WorkflowModal
      v-if="showStrategy"
      type="strategy"
      @close="showStrategy = false"
    />

    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { computed, nextTick, ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import WorkflowModal from '../components/WorkflowModal.vue'
import ToastMessage from '../components/ToastMessage.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import { getUser, getTheme, setTheme } from '../store/app'
import { fetchLeadsApi, fetchChatsApi } from '../api/mock'
import { drawTrendChart, drawSourceChart, drawFunnelChart } from '../utils/charts'
import { useToast } from '../composables/useToast'
import { canManageRules } from '../utils/permission'

const user = getUser()

const leads = ref([])
const chats = ref([])
const loading = ref(false)
const error = ref('')

const showArchive = ref(false)
const showSegment = ref(false)
const showStrategy = ref(false)

const trendRef = ref(null)
const sourceRef = ref(null)
const funnelRef = ref(null)

const { toastText, toastVisible, showToast } = useToast()

const canRules = computed(() => canManageRules(user))

const highCount = computed(() => leads.value.filter(item => item.level === 'high').length)
const followCount = computed(() => {
  return leads.value.filter(item => item.status?.includes('待') || item.level === 'follow').length
})
const newCount = computed(() => leads.value.filter(item => item.level === 'new').length)
const hotLeads = computed(() => leads.value.filter(item => item.level === 'high'))

function renderCharts() {
  const dark = getTheme() === 'dark'
  drawTrendChart(trendRef.value, dark)
  drawSourceChart(sourceRef.value, dark)
  drawFunnelChart(funnelRef.value, dark)
}

async function loadDashboard() {
  loading.value = true
  error.value = ''

  try {
    const [leadRes, chatRes] = await Promise.all([
      fetchLeadsApi({
        page: 1,
        pageSize: 50,
        level: 'all'
      }),
      fetchChatsApi()
    ])

    leads.value = leadRes?.list || []
    chats.value = chatRes || []

    await nextTick()

    if (hotLeads.value.length) {
      renderCharts()
    }
  } catch (e) {
    error.value = e?.message || '加载工作台失败'
  } finally {
    loading.value = false
  }
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')

  if (hotLeads.value.length) {
    renderCharts()
  }

  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

document.body.classList.toggle('dark', getTheme() === 'dark')
await loadDashboard()
</script>
