sap.ui.define([
  'sap/ui/core/mvc/Controller',
  'sap/m/MessageBox',
  'cc/ase/poker/table/util/cookies',
  'cc/ase/poker/table/util/table'
], (
  Controller,
  MessageBox,
  cookies,
  table
) => {
  return Controller.extend('cc.ase.poker.table.controller.App', {
    onInit(...args) {
      Controller.prototype?.onInit?.apply(this, args)
      this._init()
    },

    async _init() {
      // get current table data
      await this._updateModel()
      // join table if not already joined
      await this._joinTable()
      // start polling for table updates
      setInterval(() => this._updateModel(), 1000)
    },

    _getPlayer() {
      let player = null
      try {
        const jwt = cookies.get('jwt')
        const { user_id, user_name } = JSON.parse(atob(jwt.split('.')[1]))
        player = { id: user_id, name: user_name }
      } catch ({ stack }) {
        console.error(stack)
      }
      return player
    },

    async _updateModel() {
      try {
        const player = this._getPlayer()
        const { state, currentPlayer, players, bets, pot, communityCards, playerCards, winner, winnerHand } = await table.fetch()
        const view = this.getView()
        const model = view.getModel()
        model.setProperty('/', Object.assign({}, model.getProperty('/'), {
          state,
          player,
          currentPlayer,
          players,
          bets: bets ? Object.entries(bets).map(([id, bet]) => {
            const name = players.find(p => p.id === id)?.name
            return { name, bet }
          }) : [],
          pot,
          communityCards,
          playerCards,
          winner,
          winnerHand,
          start: {
            visible: player.id === players?.[0]?.id,
            enabled: (state === 0 || state === 5) && (players.length > 1)
          },
          actions: {
            enabled: currentPlayer?.id === player.id
          }
        }))
      } catch ({ message, stack }) {
        console.error(stack)
        MessageBox.error(message)
      }
    },

    async _joinTable() {
      try {
        const view = this.getView()
        const model = view.getModel()
        const { player, players } = model.getProperty('/')
        if (!players.find(({ name }) => name === player.name)) {
          await table.join()
        }
      } catch ({ message, stack }) {
        console.error(stack)
        MessageBox.error(message)
      }
    },

    async start() {
      try {
        await table.start()
      } catch ({ message, stack }) {
        console.error(stack)
        MessageBox.error(message)
      }
    },

    async action(action, ...args) {
      try {
        await table.action(action, ...args)
      } catch ({ message, stack }) {
        console.error(stack)
        MessageBox.error(message)
      }
    },

    logout() {
      window.location.replace(`${window.location.origin}/login`)
    }
  })
})
