import request from '../request'

export function getRulesRequest() {
  return request({
    url: '/rules',
    method: 'get'
  })
}

export function updateRulesRequest(data) {
  return request({
    url: '/rules',
    method: 'patch',
    data
  })
}
