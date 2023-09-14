sap.ui.define([], () => {
  return {
    get(name) {
      let cookie = null
      try {
        // https://www.geekstrick.com/snippets/how-to-parse-cookies-in-javascript/
        const cookies =
          document.cookie
            .split(';')
            .map(v => v.split('='))
            .reduce((acc, v) => {
              acc[decodeURIComponent(v[0].trim())] = decodeURIComponent(v[1].trim())
              return acc
            }, {})
        cookie = cookies[name]
      } catch ({ stack }) {
        console.error(stack)
      }
      return cookie
    }
  }
})
