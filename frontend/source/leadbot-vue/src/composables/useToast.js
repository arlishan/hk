import { ref } from 'vue'

const toastText = ref('')
const toastVisible = ref(false)

let timer = null

export function useToast() {
  function showToast(text = '') {
    toastText.value = text
    toastVisible.value = true

    if (timer) {
      clearTimeout(timer)
    }

    timer = setTimeout(() => {
      toastVisible.value = false
    }, 1800)
  }

  function hideToast() {
    toastVisible.value = false
    if (timer) {
      clearTimeout(timer)
      timer = null
    }
  }

  return {
    toastText,
    toastVisible,
    showToast,
    hideToast
  }
}
