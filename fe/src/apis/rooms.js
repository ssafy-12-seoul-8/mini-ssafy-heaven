import { MessageType, getMessageType } from '@/enums/MessageType'
import { http, stomp } from './instance'

const baseRoomPath = 'api/rooms'

export const roomApi = {
  create: (request) => http.post(baseRoomPath, request),
  confirmCreation: (id, request) => http.patch(`${baseRoomPath}/${id}`, request),
  getDetail: (id) => http.get(`${baseRoomPath}/${id}`),
  join: (id) => http.post(`${baseRoomPath}/${id}`),
  getAll: (cursor, size) => http.get(`${baseRoomPath}?cursor=${cursor}&size=${size}`),
}

const parseSocketMessage = (res) => {
  const data = JSON.parse(res.body)
  const type = getMessageType(data.type)

  type.action(data.payload)
}

export const roomSocket = {
  connected: () => stomp.connected,
  enter: (id, body, onConnect, onError) => {
    const onEnter = () => {
      stomp.subscribe(id, (res) => parseSocketMessage(res))
      stomp.send(id, MessageType.ENTER.lower, body)

      if (onConnect) {
        onConnect()
      }
    }

    stomp.connect(onEnter, onError)
  },
  ready: (id, body) => stomp.send(id, MessageType.READY.lower, body),
  exit: async (id, body) => {
    stomp.send(id, MessageType.EXIT.lower, body)
    await stomp.disconnect()
  },
  chat: (id, body) => stomp.send(id, MessageType.TALK.lower, body),
  start: (id) => stomp.send(id, MessageType.START.lower),
  score: async (id, body) => stomp.send(id, MessageType.SCORE.lower, body),
  gameStart: (id, body) => stomp.send(id, `${MessageType.GAME.lower}/start`, body),
  gameSetAnswer: (id, body) => stomp.send(id, `${MessageType.GAME.lower}/set-answer`, body),
  gameConfirm: (id, body) => stomp.send(id, `${MessageType.GAME.lower}/confirm`, body),
  gameRoundStart: (id, body) => stomp.send(id, `${MessageType.GAME.lower}/round-start`, body),
  gameTry: (id, body) => stomp.send(id, `${MessageType.GAME.lower}/try`, body),
  allOver: (id) => stomp.send(id, MessageType.ALL_OVER.lower),
  backRoom: (id, body) => stomp.send(id, MessageType.BACK_ROOM.lower, body),
}
