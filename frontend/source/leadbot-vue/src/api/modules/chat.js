import request from '../request'

export function getChatsRequest() {
  return request({
    url: '/chats',
    method: 'get'
  })
}

export function sendChatRequest(data) {
  return request({
    url: '/chats',
    method: 'post',
    data
  })
}
