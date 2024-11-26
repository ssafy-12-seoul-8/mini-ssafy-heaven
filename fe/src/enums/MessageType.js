import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { useChatStore } from '@/stores/chats'
import { useRoomStore } from '@/stores/rooms'
import { RoomPlayerStatus } from './RoomPlayerStatus'
import { findGameMessageType, findGameType } from './GameType'
import { useRoomGameStore } from '@/stores/roomGames'
import { useBaseballStore } from '@/stores/baseball'

const handleChat = (message) => {
  const { addChat } = useChatStore()

  addChat(message)
}

const handlePlayerUpdate = (players) => {
  const { updatePlayers } = useRoomPlayerStore()
  const { updateReadyCount } = useRoomStore()
  const readyCount = players.filter(
    (player) => player.status === RoomPlayerStatus.READY.value,
  ).length

  updatePlayers(players)
  updateReadyCount(readyCount, players.length)
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

const doStart = (data) => {
  const { updateStatus } = useRoomStore()
  const { gameJustStart } = useBaseballStore()

  gameJustStart()
  updateStatus(data.status)
}

const doGame = (data) => {
  const gameType = findGameType(data.gameType)
  const gameMessageType = findGameMessageType(data.gameMessageType)

  gameType.instance.doAction(gameMessageType, data.payload)
}

const doScore = (data) => {
  const { updatePlayers } = useRoomPlayerStore()

  updatePlayers(data.roomPlayers)
}

const doAllOver = (data) => {
  const { updatePlayers } = useRoomPlayerStore()
  const { roomOver } = useRoomStore()
  const { clearCurrentGame } = useRoomGameStore()
  const { gameOver } = useBaseballStore()

  gameOver()
  updatePlayers(data.roomPlayers)
  clearCurrentGame()
  roomOver()
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
  START: {
    name: 'START',
    lower: 'start',
    action: doStart,
  },
  GAME: {
    name: 'GAME',
    lower: 'game',
    action: doGame,
  },
  SCORE: {
    name: 'SCORE',
    lower: 'score',
    action: doScore,
  },
  GAME_OVER: {
    name: 'GAME_OVER',
    lower: 'game-over',
  },
  ALL_OVER: {
    name: 'ALL_OVER',
    lower: 'all-over',
    action: doAllOver,
  },
  BACK_ROOM: {
    name: 'BACK_ROOM',
    lower: 'back',
  },
}

export const getMessageType = (type) => {
  return Object.entries(MessageType).find((entry) => type === entry[1].name)[1]
}
