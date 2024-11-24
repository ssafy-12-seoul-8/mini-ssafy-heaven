import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { useChatStore } from '@/stores/chats'
import { useRoomStore } from '@/stores/rooms'

const handleChat = (message) => {
  const { addChat } = useChatStore()

  addChat(message)
}

const handlePlayerUpdate = (players) => {
  const { updatePlayers } = useRoomPlayerStore()
  const { updateTotalCount } = useRoomStore()

  updatePlayers(players)
  updateTotalCount(players.length)
}

const doMemberChange = (data) => {
  handlePlayerUpdate(data.players)
  handleChat(data.message)
}

const doReady = (data) => {
  const { updateReadyPlayer } = useRoomPlayerStore()
  const { updateReadyCount } = useRoomStore()

  updateReadyPlayer(data.memberId, data.status)
  updateReadyCount(data.currentReadyCount, data.totalCount)
}

export const MessageType = {
  ENTER: {
    name: 'ENTER',
    lower: 'enter',
    action: doMemberChange,
  },
  EXIT: {
    name: 'EXIT',
    lower: 'exit',
    action: doMemberChange,
  },
  TALK: {
    name: 'TALK',
    lower: 'talk',
    action: (data) => handleChat(data.message),
  },
  READY: {
    name: 'READY',
    lower: 'ready',
    action: doReady,
  },
  SCORE: {
    name: 'SCORE',
    lower: 'score',
  },
  GAME_OVER: {
    name: 'GAME_OVER',
    lower: 'game-over',
  },
  ALL_OVER: {
    name: 'ALL_OVER',
    lower: 'all-over',
  },
}

export const getMessageType = (type) => {
  return Object.entries(MessageType).find((entry) => type === entry[1].name)[1]
}
