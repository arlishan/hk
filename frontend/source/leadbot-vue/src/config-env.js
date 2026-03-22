export const API_MODE = import.meta.env.VITE_API_MODE || 'mock'
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || ''
export const APP_TITLE = import.meta.env.VITE_APP_TITLE || 'LeadBot'

export function isMockMode() {
  return API_MODE === 'mock'
}

export function isRealMode() {
  return API_MODE === 'real'
}
