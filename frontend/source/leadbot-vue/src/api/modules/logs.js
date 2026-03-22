import request from '../request'

export function getLogsRequest(id) {
  return request({
    url: `/leads/${id}/logs`,
    method: 'get'
  })
}

export function addLogRequest(id, data) {
  return request({
    url: `/leads/${id}/logs`,
    method: 'post',
    data
  })
}
