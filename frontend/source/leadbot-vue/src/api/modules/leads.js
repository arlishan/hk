import request from '../request'

export function getLeadsRequest(params = {}) {
  return request({
    url: '/leads',
    method: 'get',
    params
  })
}

export function createLeadRequest(data) {
  return request({
    url: '/leads',
    method: 'post',
    data
  })
}

export function updateLeadRequest(id, data) {
  return request({
    url: `/leads/${id}`,
    method: 'patch',
    data
  })
}

export function deleteLeadRequest(id) {
  return request({
    url: `/leads/${id}`,
    method: 'delete'
  })
}
