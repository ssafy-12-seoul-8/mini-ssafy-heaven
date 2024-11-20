import { http, stomp } from './instance'

const baseRoomPath = 'api/rooms'

export const roomApi = {
  create: (request) => http.post(baseRoomPath, request),
  confirmCreation: (id, request) => http.patch(`${baseRoomPath}/${id}`, request),
}

export const roomSocket = {
  enter: (id, onConnect, onError) => {
    const onEnter = () => {
      stomp.subscribe(id)

      if (onConnect) {
        onConnect()
      }
    }

    stomp.connect(onEnter, onError)
  },
}
