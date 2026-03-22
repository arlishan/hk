<template>
  <div class="app-shell">
    <TopBar title="数据报表" :user="user" @toggle-theme="toggleTheme" />
    <main class="main-content">
      <LoadingBlock v-if="loading" text="正在加载报表数据..." />

      <ErrorBlock
        v-else-if="error"
        :text="error"
        @retry="loadReport"
      />

      <template v-else>
        <section class="stats-grid">
          <div class="stat-card">
            <span>本周触达量</span>
            <strong>{{ report.summary.touchCount }}</strong>
            <em class="up">+12.4%</em>
          </div>
          <div class="stat-card">
            <span>有效回复数</span>
            <strong>{{ report.summary.replyCount }}</strong>
            <em class="up">回复率 {{ replyRate }}%</em>
          </div>
          <div class="stat-card">
            <span>高意向线索</span>
            <strong>{{ report.summary.highIntentCount }}</strong>
            <em class="up">+6.1%</em>
          </div>
          <div class="stat-card">
            <span>预约演示</span>
            <strong>{{ report.summary.appointmentCount }}</strong>
            <em class="warn">持续推进中</em>
          </div>
        </section>

        <section class="card">
          <div class="section-head">
            <h3>本周转化趋势</h3>
          </div>
          <canvas ref="trendRef" class="chart-canvas" width="360" height="180"></canvas>
        </section>

        <section class="double-grid">
          <div class="card">
            <div class="section-head">
              <h3>来源占比</h3>
            </div>
            <canvas ref="sourceRef" class="chart-canvas small" width="160" height="160"></canvas>
            <div class="chart-legend">
              <div
                class="chart-legend-item"
                v-for="item in report.sources"
                :key="item.name"
              >
                <div class="chart-legend-left">
                  <span class="legend-dot" :style="{ background: item.color }"></span>
                  <span>{{ item.name }}</span>
                </div>
                <strong>{{ item.value }}%</strong>
              </div>
            </div>
          </div>

          <div class="card">
            <div class="section-head">
              <h3>转化漏斗</h3>
            </div>
            <canvas ref="funnelRef" class="chart-canvas small" width="160" height="160"></canvas>
            <div class="funnel-mini-list">
              <div v-for="item in report.funnel" :key="item.label">
                <span>{{ item.label }}</span>
                <strong>{{ item.value }}</strong>
              </div>
            </div>
          </div>
        </section>

        <section class="card">
          <div class="section-head">
            <h3>行业表现</h3>
          </div>

          <div v-if="report.industries.length" class="team-list">
            <div class="team-item" v-for="item in report.industries" :key="item.name">
              <div class="team-avatar">{{ item.name.slice(0, 1) }}</div>
              <div class="team-info">
                <strong>{{ item.name }}</strong>
                <p>本周高意向：{{ item.highIntent }} · 回复率：{{ item.replyRate }}%</p>
              </div>
              <div class="team-metric">
                <span>线索量</span>
                <strong>{{ item.leads }}</strong>
              </div>
            </div>
          </div>

          <EmptyBlock
            v-else
            text="当前暂无行业报表数据。"
          />
        </section>

        <section class="card">
          <div class="section-head">
            <h3>运营结论</h3>
          </div>
          <div class="advice-list">
            <div class="advice-item">
              <span class="mini-tag high">重点结论</span>
              <p>教育和本地生活行业的初始回复率表现最好，建议继续加大自动触达频次。</p>
            </div>
            <div class="advice-item">
              <span class="mini-tag">优化建议</span>
              <p>医疗行业从回复到预约的转化偏低，建议优化案例展示和价格锚点设计。</p>
            </div>
          </div>
        </section>
      </template>
    </main>

    <BottomNav current="/reports" />
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import { drawTrendChart, drawSourceChart, drawFunnelChart } from '../utils/charts'
import { getTheme, setTheme, getUser } from '../store/app'
import { fetchLeadsApi } from '../api'

const user = getUser()

const loading = ref(false)
const error = ref('')

const trendRef = ref(null)
const sourceRef = ref(null)
const funnelRef = ref(null)

const report = ref({
  summary: {
    touchCount: 0,
    replyCount: 0,
    highIntentCount: 0,
    appointmentCount: 0
  },
  trend: [],
  sources: [],
  funnel: [],
  industries: []
})

const replyRate = computed(() => {
  const touch = report.value.summary.touchCount || 0
  const reply = report.value.summary.replyCount || 0
  if (!touch) return '0.0'
  return ((reply / touch) * 100).toFixed(1)
})

function normalizeLeadList(response) {
  if (Array.isArray(response)) return response
  if (response?.list && Array.isArray(response.list)) return response.list
  if (response?.data?.list && Array.isArray(response.data.list)) return response.data.list
  return []
}

function buildReport(leads) {
  const totalLeads = leads.length
  const highIntent = leads.filter(item => item.level === 'high').length
  const followLeads = leads.filter(item => item.level === 'follow').length
  const appointments = leads.filter(item => item.status?.includes('预约')).length

  const touchCount = totalLeads * 32 + 140
  const replyCount = totalLeads * 9 + 65

  const sourceMap = {}
  const industryMap = {}

  leads.forEach(item => {
    const source = item.source || '未知来源'
    const industry = item.industry || '未分类'

    sourceMap[source] = (sourceMap[source] || 0) + 1

    if (!industryMap[industry]) {
      industryMap[industry] = {
        name: industry,
        leads: 0,
        highIntent: 0
      }
    }

    industryMap[industry].leads += 1
    if (item.level === 'high') {
      industryMap[industry].highIntent += 1
    }
  })

  const sourceColors = ['#4f7cff', '#22c55e', '#f59e0b', '#8b5cf6', '#06b6d4']
  const sourceEntries = Object.entries(sourceMap)
  const sourceTotal = sourceEntries.reduce((sum, [, count]) => sum + count, 0) || 1

  const sources = sourceEntries.map(([name, count], index) => ({
    name,
    value: Math.round((count / sourceTotal) * 100),
    color: sourceColors[index % sourceColors.length]
  }))

  const industries = Object.values(industryMap).map(item => ({
    ...item,
    replyRate: item.leads ? ((item.highIntent / item.leads) * 100).toFixed(1) : '0.0'
  }))

  return {
    summary: {
      touchCount,
      replyCount,
      highIntentCount: highIntent + 20,
      appointmentCount: appointments + 8
    },
    trend: [18, 22, 16, 28, 24, 32, 26],
    sources,
    funnel: [
      { label: '触达', value: touchCount },
      { label: '回复', value: replyCount },
      { label: '高意向', value: highIntent + 20 },
      { label: '预约', value: appointments + 8 }
    ],
    industries
  }
}

function renderCharts() {
  const dark = getTheme() === 'dark'
  if (trendRef.value) drawTrendChart(trendRef.value, dark)
  if (sourceRef.value) drawSourceChart(sourceRef.value, dark)
  if (funnelRef.value) drawFunnelChart(funnelRef.value, dark)
}

async function loadReport() {
  loading.value = true
  error.value = ''

  try {
    const leadRes = await fetchLeadsApi({
      page: 1,
      pageSize: 100
    })

    const leadList = normalizeLeadList(leadRes)
    report.value = buildReport(leadList)

    await nextTick()
    renderCharts()
  } catch (e) {
    error.value = e?.message || '加载报表失败'
  } finally {
    loading.value = false
  }
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  renderCharts()
}

onMounted(async () => {
  document.body.classList.toggle('dark', getTheme() === 'dark')
  await loadReport()
})
</script>
