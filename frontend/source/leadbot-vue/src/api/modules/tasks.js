import request from '../request'

export function getTasksRequest() {
  return request({
    url: '/tasks',
    method: 'get'
  })
}

export function updateTaskRequest(id, data) {
  return request({
    url: `/tasks/${id}`,
    method: 'patch',
    data
  })
}
