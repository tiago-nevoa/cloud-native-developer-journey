sap.ui.define([], () => {
  const API_PATH = '/api/v1'

  const _fetch = async (path, options) => {
    const response = await fetch(path, options)
    if (!response.ok) {
      const message = await response.text()
      throw new Error(message)
    }
    return response
  }

  return {
    async fetch() {
      const response = await _fetch(API_PATH)
      const table = await response.json()
      return table
    },

    async join() {
      await _fetch(`${API_PATH}/players`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      })
    },

    async start() {
      await _fetch(`${API_PATH}/start`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      })
    },

    async action(type, ...args) {
      await _fetch(`${API_PATH}/actions`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          type,
          args
        })
      })
    }
  }
})
