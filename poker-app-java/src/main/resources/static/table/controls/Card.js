sap.ui.define(['sap/ui/core/Control'], Control => Control.extend('cc.ase.poker.table.controls.Card', {
  metadata: {
    properties: {
      suit: {
        type: 'string'
      },
      rank: {
        type: 'string'
      }
    }
  },

  _renderRank() {
    let renderRank = ''
    const rank = this.getRank()
    switch (rank) {
      case 'jack':
        renderRank = 'J'
        break
      case 'queen':
        renderRank = 'Q'
        break
      case 'king':
        renderRank = 'K'
        break
      case 'ace':
        renderRank = 'A'
        break
      default:
        renderRank = rank
    }
    return renderRank
  },

  _renderSuit() {
    const renderSuit = this.getSuit()
    return renderSuit === 'diamonds' ? 'diams' : renderSuit
  },

  renderer(oRM, oControl) {
    oRM.write('<div')
    oRM.writeControlData(oControl)
    oRM.writeClasses()
    oRM.write(`><div class="card rank-${oControl._renderRank().toLowerCase()} ${oControl._renderSuit()}">`)
    oRM.write(`<span class="rank">${oControl._renderRank()}</span>`)
    oRM.write(`<span class="suit">&${oControl._renderSuit()};</span>`)
    oRM.write('</div></div>')
  }
}))
