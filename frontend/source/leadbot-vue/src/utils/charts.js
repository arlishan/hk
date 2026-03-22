function ensureCanvas(canvas) {
  if (!canvas) return null
  const ctx = canvas.getContext('2d')
  if (!ctx) return null
  return ctx
}

function clearCanvas(ctx, canvas) {
  ctx.clearRect(0, 0, canvas.width, canvas.height)
}

function getThemeColors(dark = false) {
  return {
    lineColor: dark ? '#7da2ff' : '#4f7cff',
    textColor: dark ? '#94a3b8' : '#6b7280',
    gridColor: dark ? '#253247' : '#e7edf6',
    innerColor: dark ? '#111827' : '#ffffff'
  }
}

export function drawTrendChart(canvas, dark = false) {
  const ctx = ensureCanvas(canvas)
  if (!ctx) return

  clearCanvas(ctx, canvas)

  const { lineColor, textColor, gridColor } = getThemeColors(dark)
  const w = canvas.width
  const h = canvas.height

  const values = [18, 22, 16, 28, 24, 32, 26]
  const labels = ['一', '二', '三', '四', '五', '六', '日']
  const max = 40

  ctx.lineWidth = 1
  ctx.strokeStyle = gridColor

  for (let i = 0; i < 4; i++) {
    const y = 20 + i * 35
    ctx.beginPath()
    ctx.moveTo(30, y)
    ctx.lineTo(w - 20, y)
    ctx.stroke()
  }

  const stepX = (w - 60) / (values.length - 1)

  ctx.beginPath()
  ctx.lineWidth = 3
  ctx.strokeStyle = lineColor

  values.forEach((value, index) => {
    const x = 30 + index * stepX
    const y = h - 25 - (value / max) * 120
    if (index === 0) {
      ctx.moveTo(x, y)
    } else {
      ctx.lineTo(x, y)
    }
  })

  ctx.stroke()

  values.forEach((value, index) => {
    const x = 30 + index * stepX
    const y = h - 25 - (value / max) * 120

    ctx.beginPath()
    ctx.fillStyle = lineColor
    ctx.arc(x, y, 4, 0, Math.PI * 2)
    ctx.fill()

    ctx.fillStyle = textColor
    ctx.font = '12px sans-serif'
    ctx.textAlign = 'center'
    ctx.fillText(labels[index], x, h - 6)
  })
}

export function drawSourceChart(canvas, dark = false) {
  const ctx = ensureCanvas(canvas)
  if (!ctx) return

  clearCanvas(ctx, canvas)

  const { innerColor } = getThemeColors(dark)
  const w = canvas.width
  const h = canvas.height

  const data = [
    { value: 40, color: '#4f7cff' },
    { value: 28, color: '#22c55e' },
    { value: 20, color: '#f59e0b' },
    { value: 12, color: '#8b5cf6' }
  ]

  const total = data.reduce((sum, item) => sum + item.value, 0)
  let start = -Math.PI / 2

  data.forEach(item => {
    const angle = (item.value / total) * Math.PI * 2
    ctx.beginPath()
    ctx.moveTo(w / 2, h / 2)
    ctx.fillStyle = item.color
    ctx.arc(w / 2, h / 2, 60, start, start + angle)
    ctx.closePath()
    ctx.fill()
    start += angle
  })

  ctx.beginPath()
  ctx.fillStyle = innerColor
  ctx.arc(w / 2, h / 2, 32, 0, Math.PI * 2)
  ctx.fill()
}

export function drawFunnelChart(canvas, dark = false) {
  const ctx = ensureCanvas(canvas)
  if (!ctx) return

  clearCanvas(ctx, canvas)

  const { textColor } = getThemeColors(dark)
  const data = [
    { label: '触达', value: 324, color: '#4f7cff' },
    { label: '回复', value: 106, color: '#22c55e' },
    { label: '高意向', value: 26, color: '#f59e0b' },
    { label: '预约', value: 8, color: '#8b5cf6' }
  ]

  const max = 324
  const barHeight = 24
  const gap = 10

  data.forEach((item, index) => {
    const y = 12 + index * (barHeight + gap)
    const width = (item.value / max) * 120 + 20

    ctx.fillStyle = item.color
    ctx.fillRect(20, y, width, barHeight)

    ctx.fillStyle = textColor
    ctx.font = '12px sans-serif'
    ctx.fillText(item.label, 24, y + 16)
  })
}
