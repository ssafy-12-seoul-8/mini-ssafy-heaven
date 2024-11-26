import { useBaseballStore } from '@/stores/baseball'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { GameMessageType } from './GameType'
import { useRoomGameStore } from '@/stores/roomGames'
import { storeToRefs } from 'pinia'
import { useRoomStore } from '@/stores/rooms'

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

const setAnswer = (condition) => {
  const { setAnswer } = useBaseballStore()

  setAnswer(condition)
}

const confirm = (data) => {
  const { incrementConfirm } = useBaseballStore()
  const { currentRoom } = storeToRefs(useRoomStore())

  incrementConfirm(currentRoom.value.totalCount, data.readCount)
}

const roundStart = (data) => {
  const { stageToRoundStart } = useBaseballStore()

  stageToRoundStart(data.nextLimit)
}

export const Baseball = {
  start: {
    value: () => GameMessageType.START,
    action: start,
  },
  setAnswer: {
    value: () => GameMessageType.SET_ANSWER,
    action: setAnswer,
  },
  confirm: {
    value: () => GameMessageType.CONFIRM,
    action: confirm,
  },
  roundStart: {
    value: () => GameMessageType.ROUND_START,
    action: roundStart
  },
  doAction: (messageType, payload) => findProcess(messageType).action(payload),
}
