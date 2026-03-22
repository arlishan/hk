import request from '../request'

export function changePasswordRequest(data) {
  return request({
    url: '/auth/change-password',
    method: 'post',
    data
  })
}
