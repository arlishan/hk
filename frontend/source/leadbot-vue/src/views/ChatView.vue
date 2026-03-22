<template>
  <div class="app-shell">
    <TopBar title="AI 助手" :user="user" @toggle-theme="toggleTheme" />
    <main class="main-content">
      <section class="card">
        <div class="section-head">
          <h3>AI 获客助手</h3>
        </div>

        <div class="bot-intro">
          <div class="bot-avatar">AI</div>
          <div>
            <strong>LeadBot 智能体</strong>
            <p>支持生成邀约文案、客户分层建议、跟进方案、行业分析结论。</p>
          </div>
        </div>

        <div class="prompt-grid">
          <button class="prompt-chip" @click="fillPrompt('帮我写一段教育行业首次邀约话术')">
            教育邀约话术
          </button>
          <button class="prompt-chip" @click="fillPrompt('给我一个高意向客户的跟进方案')">
            高意向跟进
          </button>
          <button class="prompt-chip" @click="fillPrompt('分析本周获客转化问题')">
            转化问题分析
          </button>
        </div>
      </section>

      <LoadingBlock v-if="loading" text="正在加载聊天记录..." />

      <ErrorBlock
        v-else-if="error"
        :text="error"
        @retry="loadChats"
      />

      <template v-else>
        <EmptyBlock
          v-if="!chats.length"
          text="还没有聊天记录，快试试向 AI 提问吧。"
        />

        <section v-else ref="chatWindowRef" class="chat-window">
          <div
            v-for="(msg, index) in chats"
            :key="msg.id || index"
            class="msg"
            :class="msg.role"
          >
            <div class="bubble">{{ msg.text }}</div>
          </div>
        </section>
      </template>

      <section class="chat-input-bar">
        <input
          v-model="text"
          type="text"
          placeholder="例如：帮我写一段口腔门店引流的话术"
          @keydown.enter="send"
        />
        <button class="primary-btn small" @click="send">发送</button>
      </section>
    </main>

    <BottomNav current="/chat" />
    <ToastMessage :visible="toastVisible" :text="toastText" />
  </div>
</template>

<script setup>
import { nextTick, ref } from 'vue'
import TopBar from '../components/TopBar.vue'
import BottomNav from '../components/BottomNav.vue'
import ToastMessage from '../components/ToastMessage.vue'
import LoadingBlock from '../components/LoadingBlock.vue'
import EmptyBlock from '../components/EmptyBlock.vue'
import ErrorBlock from '../components/ErrorBlock.vue'
import { fetchChatsApi, sendChatApi } from '../api/mock'
import { getTheme, setTheme, getUser } from '../store/app'
import { useToast } from '../composables/useToast'

const user = getUser()
const chats = ref([])
const text = ref('')
const loading = ref(false)
const error = ref('')
const chatWindowRef = ref(null)

const { toastText, toastVisible, showToast } = useToast()

function getReply(value) {
  if (value.includes('教育')) {
    return '教育行业推荐话术：您好，我们专注帮助教育机构提升首咨转化和私域留资效率，方便了解一下您当前的招生渠道与转化瓶颈吗？'
  }
  if (value.includes('跟进')) {
    return '高意向客户建议：首次回复后 24 小时内二次触达，补充案例、试用策略和明确下一步动作。'
  }
  if (value.includes('分析')) {
    return '本周转化问题分析：当前触达量充足，但从有效回复到高意向的转化链路偏弱，建议优化首次文案与二次跟进节奏。'
  }
  if (value.includes('口腔')) {
    return '口腔门店话术建议：您好，我们帮助口腔门店通过 AI 自动触达和私域承接提升到店咨询率，是否方便交流一下您目前的拉新方式？'
  }
  return '建议先补充客户行业、规模、当前获客渠道和核心问题，我可以继续为你生成更精准的方案。'
}

async function scrollToBottom() {
  await nextTick()
  if (chatWindowRef.value) {
    chatWindowRef.value.scrollTop = chatWindowRef.value.scrollHeight
  }
}

async function loadChats() {
  loading.value = true
  error.value = ''

  try {
    chats.value = await fetchChatsApi()
    await scrollToBottom()
  } catch (e) {
    error.value = e.message || '加载聊天记录失败'
    chats.value = []
  } finally {
    loading.value = false
  }
}

function fillPrompt(prompt) {
  text.value = prompt
}

async function send() {
  const value = text.value.trim()
  if (!value) return

  try {
    chats.value = await sendChatApi(value, getReply(value))
    text.value = ''
    await scrollToBottom()
    showToast('AI 已生成回复')
  } catch (e) {
    showToast(e.message || '发送失败')
  }
}

function toggleTheme() {
  const next = getTheme() === 'dark' ? 'light' : 'dark'
  setTheme(next)
  document.body.classList.toggle('dark', next === 'dark')
  showToast(next === 'dark' ? '已切换深色模式' : '已切换浅色模式')
}

document.body.classList.toggle('dark', getTheme() === 'dark')
await loadChats()
</script>
