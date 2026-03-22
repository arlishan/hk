import axios from 'axios'
import router from '../router'
import { API_BASE_URL } from '../config-env'
import { getToken, logout } from '../store/app'
import {
  SUCCESS_CODE,
  UNAUTHORIZED_CODE,
  FORBIDDEN_CODE,
  DEFAULT_ERROR_MESSAGE
} from '../constants/response'

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

request.interceptors.request.use(
  config => {
    const token = getToken()
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => Promise.reject(error)
)

request.interceptors.response.use(
  response => {
    const res = response.data

    if (typeof res !== 'object' || res === null) {
      return res
    }

    if (res.code === SUCCESS_CODE || res.success === true) {
      return res.data
    }

    if (res.code === UNAUTHORIZED_CODE) {
      logout()
      router.push('/login')
      return Promise.reject(new Error(res.message || '登录已过期，请重新登录'))
    }

    if (res.code === FORBIDDEN_CODE) {
      router.push('/403')
      return Promise.reject(new Error(res.message || '无权限访问'))
    }

    return Promise.reject(new Error(res.message || DEFAULT_ERROR_MESSAGE))
  },
  error => {
    const status = error?.response?.status
    const message =
      error?.response?.data?.message ||
      error?.message ||
      DEFAULT_ERROR_MESSAGE

    if (status === 401) {
      logout()
      router.push('/login')
    } else if (status === 403) {
      router.push('/403')
    }

    return Promise.reject(new Error(message))
  }
)

export default request
