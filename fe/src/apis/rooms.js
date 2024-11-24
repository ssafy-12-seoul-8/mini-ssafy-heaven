import { MessageType, getMessageType } from '@/constants/MessageType'
import { http, stomp } from './instance'

const baseRoomPath = 'api/rooms'

export const roomApi = {
  create: (request) => http.post(baseRoomPath, request),
  confirmCreation: (id, request) => http.patch(`${baseRoomPath}/${id}`, request),
  getDetail: (id) => http.get(`${baseRoomPath}/${id}`),
  join: (id) => http.post(`${baseRoomPath}/${id}`),
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
  exit: (id, body) => {
    stomp.send(id, MessageType.EXIT.lower, body)
    stomp.disconnect()
  },
  chat: (id, body) => stomp.send(id, MessageType.TALK.lower, body),
}
