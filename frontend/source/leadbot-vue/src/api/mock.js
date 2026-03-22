import { isMockMode } from '../config-env'
import {
  getLeads,
  saveLeads,
  getTasks,
  saveTasks,
  getChats,
  saveChats,
  getLogs,
  saveLogs,
  getMembers,
  saveMembers,
  getChannels,
  saveChannels,
  getRules,
  saveRules
} from '../store/app'

import { loginRequest, getProfileRequest } from './modules/auth'
import { changePasswordRequest } from './modules/account'
import {
  getLeadsRequest,
  createLeadRequest,
  updateLeadRequest,
  deleteLeadRequest
} from './modules/leads'
import { getTasksRequest, updateTaskRequest } from './modules/tasks'
import { getChatsRequest, sendChatRequest } from './modules/chat'
import { getLogsRequest, addLogRequest } from './modules/logs'
import {
  getMembersRequest,
  createMemberRequest,
  updateMemberRequest,
  deleteMemberRequest
} from './modules/members'
import { getChannelsRequest, updateChannelRequest } from './modules/channels'
import { getRulesRequest, updateRulesRequest } from './modules/rules'

const users = [
  {
    username: 'admin',
    password: '123456',
    token: 'vue-mock-token-admin',
    profile: {
      name: '系统管理员',
      role: 'ADMIN',
      avatar: 'A'
    }
  },
  {
    username: 'manager',
    password: '123456',
    token: 'vue-mock-token-manager',
    profile: {
      name: '刘经理',
      role: 'MANAGER',
      avatar: 'L'
    }
  },
  {
    username: 'user',
    password: '123456',
    token: 'vue-mock-token-user',
    profile: {
      name: '王专员',
      role: 'USER',
      avatar: 'W'
    }
  }
]

function wait(ms = 300) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

/* =========================
 * mock methods
 * ========================= */

async function mockLogin(payload) {
  await wait()
  const user = users.find(
    item => item.username === payload.username && item.password === payload.password
  )
  if (!user) {
    throw new Error('账号或密码错误')
  }
  return {
    token: user.token,
    user: user.profile
  }
}

async function mockProfile() {
  await wait(100)
  return users[0].profile
}

async function mockChangePassword(payload) {
  await wait(150)
  if (!payload.oldPassword || !payload.newPassword) {
    throw new Error('请输入完整密码信息')
  }
  return true
}

async function mockGetLeads(params = {}) {
  await wait(120)

  let data = [...getLeads()]
  const keyword = params.keyword?.trim() || ''
  const level = params.level || 'all'
  const page = Number(params.page || 1)
  const pageSize = Number(params.pageSize || 10)

  if (level && level !== 'all') {
    data = data.filter(item => item.level === level)
  }

  if (keyword) {
    data = data.filter(
      item =>
        item.name.includes(keyword) ||
        item.company.includes(keyword) ||
        item.tags.join(',').includes(keyword)
    )
  }

  const total = data.length
  const start = (page - 1) * pageSize
  const end = start + pageSize
  const list = data.slice(start, end)

  return {
    list,
    total,
    page,
    pageSize
  }
}

async function mockCreateLead(payload) {
  await wait(120)
  const leads = getLeads()
  const logs = getLogs()
  const id = Date.now()

  const lead = {
    id,
    name: payload.name,
    company: payload.company,
    industry: payload.industry || '未分类',
    phone: payload.phone || '未填写',
    level: 'new',
    status: '新线索',
    source: '手动录入',
    tags: ['新线索', '手动录入'],
    note: '建议先进行首次触达，确认客户需求和预算区间。',
    lastFollow: '刚刚'
  }

  leads.unshift(lead)
  logs[id] = [
    {
      time: '刚刚',
      title: '线索创建',
      desc: '系统已完成客户建档基础录入。'
    }
  ]

  saveLeads(leads)
  saveLogs(logs)
  return lead
}

async function mockUpdateLead(id, patch) {
  await wait(120)
  const leads = getLeads()
  const target = leads.find(item => item.id === id)
  if (!target) {
    throw new Error('客户不存在')
  }
  Object.assign(target, patch)
  saveLeads(leads)
  return target
}

async function mockDeleteLead(id) {
  await wait(120)
  const leads = getLeads().filter(item => item.id !== id)
  const logs = getLogs()
  delete logs[id]
  saveLeads(leads)
  saveLogs(logs)
  return true
}

async function mockGetTasks() {
  await wait(120)
  return getTasks()
}

async function mockUpdateTask(id, done) {
  await wait(120)
  const tasks = getTasks()
  const task = tasks.find(item => item.id === id)
  if (!task) {
    throw new Error('任务不存在')
  }
  task.done = done
  saveTasks(tasks)
  return task
}

async function mockGetChats() {
  await wait(120)
  return getChats()
}

async function mockSendChat(text, reply) {
  await wait(120)
  const chats = getChats()
  chats.push({ role: 'user', text })
  chats.push({ role: 'bot', text: reply })
  saveChats(chats)
  return chats
}

async function mockGetLogs(id) {
  await wait(100)
  const logs = getLogs()
  return logs[id] || []
}

async function mockAddLog(id, log) {
  await wait(100)
  const logs = getLogs()
  if (!logs[id]) logs[id] = []
  logs[id].unshift(log)
  saveLogs(logs)
  return logs[id]
}

async function mockGetMembers() {
  await wait(120)
  return getMembers()
}

async function mockSaveMember(payload) {
  await wait(120)
  const members = getMembers()
  if (payload.id) {
    const target = members.find(item => item.id === payload.id)
    if (!target) {
      throw new Error('成员不存在')
    }
    Object.assign(target, payload)
  } else {
    members.unshift({ ...payload, id: Date.now() })
  }
  saveMembers(members)
  return members
}

async function mockDeleteMember(id) {
  await wait(120)
  const members = getMembers().filter(item => item.id !== id)
  saveMembers(members)
  return true
}

async function mockGetChannels() {
  await wait(120)
  return getChannels()
}

async function mockUpdateChannel(payload) {
  await wait(120)
  const channels = getChannels()
  const target = channels.find(item => item.id === payload.id)
  if (!target) {
    throw new Error('渠道不存在')
  }
  Object.assign(target, payload)
  saveChannels(channels)
  return channels
}

async function mockGetRules() {
  await wait(120)
  return getRules()
}

async function mockUpdateRules(payload) {
  await wait(120)
  saveRules(payload)
  return payload
}

/* =========================
 * adapter methods
 * ========================= */

export async function loginApi(payload) {
  return isMockMode() ? mockLogin(payload) : loginRequest(payload)
}

export async function profileApi() {
  return isMockMode() ? mockProfile() : getProfileRequest()
}

export async function changePasswordApi(payload) {
  return isMockMode() ? mockChangePassword(payload) : changePasswordRequest(payload)
}

export async function fetchLeadsApi(params = {}) {
  if (isMockMode()) return mockGetLeads(params)
  return await getLeadsRequest(params)
}

export async function createLeadApi(payload) {
  return isMockMode() ? mockCreateLead(payload) : createLeadRequest(payload)
}

export async function updateLeadApi(id, patch) {
  return isMockMode() ? mockUpdateLead(id, patch) : updateLeadRequest(id, patch)
}

export async function deleteLeadApi(id) {
  return isMockMode() ? mockDeleteLead(id) : deleteLeadRequest(id)
}

export async function fetchTasksApi() {
  return isMockMode() ? mockGetTasks() : getTasksRequest()
}

export async function updateTaskApi(id, done) {
  return isMockMode() ? mockUpdateTask(id, done) : updateTaskRequest(id, { done })
}

export async function fetchChatsApi() {
  return isMockMode() ? mockGetChats() : getChatsRequest()
}

export async function sendChatApi(text, reply) {
  if (isMockMode()) return mockSendChat(text, reply)

  const res = await sendChatRequest({ text })
  const chats = getChats()
  chats.push({ role: 'user', text })
  chats.push({ role: 'bot', text: res.reply || '已收到你的问题。' })
  saveChats(chats)
  return chats
}

export async function fetchLogsApi(id) {
  return isMockMode() ? mockGetLogs(id) : getLogsRequest(id)
}

export async function addLogApi(id, data) {
  return isMockMode() ? mockAddLog(id, data) : addLogRequest(id, data)
}

export async function fetchMembersApi() {
  return isMockMode() ? mockGetMembers() : getMembersRequest()
}

export async function saveMemberApi(payload) {
  if (isMockMode()) return mockSaveMember(payload)
  if (payload.id) {
    return updateMemberRequest(payload.id, payload)
  }
  return createMemberRequest(payload)
}

export async function deleteMemberApi(id) {
  return isMockMode() ? mockDeleteMember(id) : deleteMemberRequest(id)
}

export async function fetchChannelsApi() {
  return isMockMode() ? mockGetChannels() : getChannelsRequest()
}

export async function updateChannelApi(payload) {
  return isMockMode() ? mockUpdateChannel(payload) : updateChannelRequest(payload.id, payload)
}

export async function fetchRulesApi() {
  return isMockMode() ? mockGetRules() : getRulesRequest()
}

export async function updateRulesApi(payload) {
  return isMockMode() ? mockUpdateRules(payload) : updateRulesRequest(payload)
}
