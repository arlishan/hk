import request from '../request'

export function loginRequest(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getProfileRequest() {
  return request({
    url: '/auth/profile',
    method: 'get'
  })
}
