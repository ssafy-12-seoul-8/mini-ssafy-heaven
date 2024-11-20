import { MessageType } from '@/constants/MessageType'
import { http, stomp } from './instance'

const baseRoomPath = 'api/rooms'

export const roomApi = {
  create: (request) => http.post(baseRoomPath, request),
  confirmCreation: (id, request) => http.patch(`${baseRoomPath}/${id}`, request),
}

export const roomSocket = {
  enter: (id, body, onConnect, onError) => {
    const onEnter = () => {
      stomp.subscribe(id, (res) => console.log(res, JSON.parse(res.body)))
      stomp.send(id, MessageType.ENTER.lower, body)

      if (onConnect) {
        onConnect()
      }
    }

    stomp.connect(onEnter, onError)
  },
}
