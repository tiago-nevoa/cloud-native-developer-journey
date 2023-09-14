sap.ui.define([
  'sap/ui/core/mvc/Controller',
  'sap/m/MessageBox',
], (
  Controller,
  MessageBox
) => Controller.extend('cc.ase.poker.login.controller.App', {
  async login() {
    try {
      const view = this.getView()
      const model = view.getModel()
      const { username, password } = model.getProperty('/')
      const { ok } = await fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          username,
          password
        })
      })
      if (ok) {
        window.location.replace(window.location.origin)
      } else {
        const i18n = view.getModel('i18n')
        const resourceBundle = i18n.getResourceBundle()
        const text = resourceBundle.getText('Failed')
        MessageBox.error(text)
      }
    } catch ({ stack }) {
      console.error(stack)
    }
  }
}))
