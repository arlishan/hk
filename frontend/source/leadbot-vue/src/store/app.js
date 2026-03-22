const KEYS = {
  token: 'leadbot_vue_token',
  user: 'leadbot_vue_user',
  leads: 'leadbot_vue_leads',
  tasks: 'leadbot_vue_tasks',
  chats: 'leadbot_vue_chats',
  logs: 'leadbot_vue_logs',
  theme: 'leadbot_vue_theme',
  members: 'leadbot_vue_members',
  channels: 'leadbot_vue_channels',
  rules: 'leadbot_vue_rules'
}

export const defaultLeads = [
  {
    id: 1,
    name: '张晓峰',
    company: '启航教育',
    industry: '教育培训',
    phone: '138****2234',
    level: 'high',
    status: '待跟进',
    source: '抖音投流',
    tags: ['高意向', '校区扩张', '已回复'],
    note: '建议 24 小时内完成二次跟进，并发送教育行业成功案例，推动预约演示。',
    lastFollow: '今天 10:20'
  },
  {
    id: 2,
    name: '刘美琳',
    company: '城南口腔',
    industry: '医疗口腔',
    phone: '156****9021',
    level: 'follow',
    status: '待回访',
    source: 'AI 外呼',
    tags: ['门店引流', '待报价'],
    note: '建议补充报价区间与到店转化路径，适合推进试用合作。',
    lastFollow: '今天 09:10'
  },
  {
    id: 3,
    name: '陈志远',
    company: '悦享健身',
    industry: '本地生活',
    phone: '185****1209',
    level: 'new',
    status: '新线索',
    source: '表单收集',
    tags: ['新线索', '健身房'],
    note: '客户处于需求确认阶段，建议用低门槛试用方案切入。',
    lastFollow: '今天 08:25'
  }
]

export const defaultTasks = [
  { id: 1, title: '跟进张晓峰，确认演示时间', desc: '优先级：高', done: false },
  { id: 2, title: '向城南口腔发送报价方案', desc: '优先级：中', done: false },
  { id: 3, title: '复盘本周 AI 外呼数据', desc: '优先级：中', done: true }
]

export const defaultChats = [
  {
    role: 'bot',
    text: '你好，我可以根据你的客户画像，自动生成获客话术、跟进建议和成交推进策略。'
  }
]

export const defaultLogs = {
  1: [
    {
      time: '今天 10:20',
      title: '客户回复',
      desc: '客户已回复并表达对招生转化方案的兴趣。'
    }
  ]
}

export const defaultMembers = [
  { id: 1, name: '刘经理', role: '销售负责人', leads: 48, avatar: 'L' },
  { id: 2, name: '王珊', role: '招商主管', leads: 35, avatar: 'W' },
  { id: 3, name: '陈宇', role: '客户经理', leads: 28, avatar: 'C' },
  { id: 4, name: '赵宁', role: 'AI 运营专员', leads: 31, avatar: 'Z' }
]

export const defaultChannels = [
  { id: 1, name: '抖音投流', budget: '当前预算：¥12,000 / 周' },
  { id: 2, name: '公众号留资', budget: '当前预算：¥3,500 / 周' },
  { id: 3, name: 'AI 外呼渠道', budget: '当前预算：¥5,000 / 周' }
]

export const defaultRules = {
  timeRange: '工作日 09:00 - 11:30、14:00 - 18:00',
  followRule: 'A 类每天；B 类隔天；C 类每周 2 次',
  industryRule: '教育优先推演示，本地生活优先推到店引流，医疗优先推咨询转化'
}

export function readStorage(key, fallback = null) {
  try {
    const raw = localStorage.getItem(key)
    return raw ? JSON.parse(raw) : fallback
  } catch {
    return fallback
  }
}

export function writeStorage(key, value) {
  localStorage.setItem(key, JSON.stringify(value))
}

export function getToken() {
  return localStorage.getItem(KEYS.token)
}

export function setToken(token) {
  localStorage.setItem(KEYS.token, token)
}

export function removeToken() {
  localStorage.removeItem(KEYS.token)
}

export function isLogin() {
  return !!getToken()
}

export function getUser() {
  return readStorage(KEYS.user, null)
}

export function getUserRole() {
  const user = getUser()
  return user?.role || ''
}

export function setUser(user) {
  writeStorage(KEYS.user, user)
}

export function clearUser() {
  localStorage.removeItem(KEYS.user)
}

export function getTheme() {
  return localStorage.getItem(KEYS.theme) || 'light'
}

export function setTheme(theme) {
  localStorage.setItem(KEYS.theme, theme)
}

export function getLeads() {
  return readStorage(KEYS.leads, defaultLeads)
}

export function saveLeads(data) {
  writeStorage(KEYS.leads, data)
}

export function getTasks() {
  return readStorage(KEYS.tasks, defaultTasks)
}

export function saveTasks(data) {
  writeStorage(KEYS.tasks, data)
}

export function getChats() {
  return readStorage(KEYS.chats, defaultChats)
}

export function saveChats(data) {
  writeStorage(KEYS.chats, data)
}

export function getLogs() {
  return readStorage(KEYS.logs, defaultLogs)
}

export function saveLogs(data) {
  writeStorage(KEYS.logs, data)
}

export function getMembers() {
  return readStorage(KEYS.members, defaultMembers)
}

export function saveMembers(data) {
  writeStorage(KEYS.members, data)
}

export function getChannels() {
  return readStorage(KEYS.channels, defaultChannels)
}

export function saveChannels(data) {
  writeStorage(KEYS.channels, data)
}

export function getRules() {
  return readStorage(KEYS.rules, defaultRules)
}

export function saveRules(data) {
  writeStorage(KEYS.rules, data)
}

export function logout() {
  removeToken()
  clearUser()
}

export { KEYS }
