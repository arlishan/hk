import request from '../request'

export function getChannelsRequest() {
  return request({
    url: '/channels',
    method: 'get'
  })
}

export function updateChannelRequest(id, data) {
  return request({
    url: `/channels/${id}`,
    method: 'patch',
    data
  })
}
