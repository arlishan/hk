import request from '../request'

export function getMembersRequest() {
  return request({
    url: '/members',
    method: 'get'
  })
}

export function createMemberRequest(data) {
  return request({
    url: '/members',
    method: 'post',
    data
  })
}

export function updateMemberRequest(id, data) {
  return request({
    url: `/members/${id}`,
    method: 'patch',
    data
  })
}

export function deleteMemberRequest(id) {
  return request({
    url: `/members/${id}`,
    method: 'delete'
  })
}
