import { useBaseballStore } from '@/stores/baseball'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { GameMessageType } from './GameType'
import { useRoomGameStore } from '@/stores/roomGames'

const findProcess = (messageType) =>
  Object.entries(Baseball).find((entry) => {
    const process = entry[1]

    if (typeof process !== 'object') {
      return false
    }

    return messageType === process.value()
  })[1]

const start = (condition) => {
  const { updateCurrentGame } = useRoomGameStore()
  const { updateCondition } = useBaseballStore()
  const { getTagger } = useRoomPlayerStore()

  updateCurrentGame('숫자야구')

  const tagger = getTagger(condition.taggerId)

  updateCondition(condition, tagger)
}

export const Baseball = {
  start: {
    value: () => GameMessageType.START,
    action: start,
  },
  doAction: (messageType, payload) => findProcess(messageType).action(payload),
}
