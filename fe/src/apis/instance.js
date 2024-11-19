import { Client } from '@stomp/stompjs'
import axios from 'axios'
import SockJS from 'sockjs-client/dist/sockjs'

export const http = axios.create({
  baseURL: import.meta.env.VITE_REST_API_URL,
})

const onDebug = (str) => console.log(str)
const getSocketClient = () => {
  const client = new Client({
    brokerURL: import.meta.env.VITE_SOCKET_URL,
    connectHeaders: {
      login: '회원 구현 이후',
      passcode: '회원 구현 이후',
    },
    debug: onDebug,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  })

  if (typeof WebSocket !== 'function') {
    client.webSocketFactory = () => new SockJS(`${import.meta.env.VITE_REST_API_URL}/stomp`)
  }

  return client
}
const client = getSocketClient()

export const stomp = {
  connect: (onConnect, onError) => {
    client.onConnect = () => {
      if (onConnect) {
        onConnect()
      }
    }
    client.onStompError = (frame) => {
      console.error(frame.body)

      if (onError) {
        onError()
      }
    }

    client.activate()
  },
  subscribe: (id) => {
    client.subscribe(`/sub/game/${id}`)
    client.subscribe(`/sub/chat/${id}`)
  },
}
