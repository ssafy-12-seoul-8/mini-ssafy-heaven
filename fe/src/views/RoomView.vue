<template>
  <div class="h-full w-full">
    <div class="h-full w-full flex flex-col justify-between">
      <RoomContents />
      <ChatBox :player="currentPlayer" />
    </div>
    <div id="player-list-container-wrapper" class="w-screen h-5/6 absolute left-0">
      <PlayerList
        @ready="toggleReady"
        @exit="handleExit"
        @start="handleStart"
        @to-room="handleBackToRoom"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue'
import PlayerList from '@/components/PlayerList.vue'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'
import { roomApi, roomSocket } from '@/apis/rooms'
import { useRoute } from 'vue-router'
import { useRoomStore } from '@/stores/rooms'
import { useRouter } from 'vue-router'
import ChatBox from '@/components/ChatBox.vue'
import RoomContents from '@/components/RoomContents.vue'
import { useChatStore } from '@/stores/chats'
import { useRoomGameStore } from '@/stores/roomGames'

const router = useRouter()
const { params } = useRoute()
const roomPlayerStore = useRoomPlayerStore()
const roomStore = useRoomStore()
const chatStore = useChatStore()
const roomGameStore = useRoomGameStore()
const { currentRoom, isJustOver } = storeToRefs(roomStore)
const { currentPlayer } = storeToRefs(roomPlayerStore)
const { roomGames } = storeToRefs(roomGameStore)
const { updatePlayers } = roomPlayerStore
const { updateRoomGames, updateCurrentGame } = roomGameStore
const { fetchRoomDetail, storeRoomIdInSession, clearCurrentRoom } = roomStore
const { clearChats } = chatStore

onMounted(() => {
  validatePlayer()
})

onUnmounted(() => {
  clearChats()
  clearCurrentRoom()
})

const validatePlayer = () => {
  roomApi
    .getDetail(params.id)
    .then((res) => fetchRoomAndPlayers(res.data))
    .then(() => {
      const storedInSession = sessionStorage.getItem('currentRoomId')
      const myNickname = JSON.parse(sessionStorage.getItem('me')).nickname
      console.log(storedInSession)

      storedInSession
        ? roomSocket.enter(params.id)
        : roomSocket.enter(params.id, { nickname: myNickname })

      storeRoomIdInSession(params.id)
    })
    .then(() => {
      const first = roomGames.value[0]

      updateCurrentGame(first.title)
    })
    .catch((err) => handleError(err))
}

const fetchRoomAndPlayers = (room) => {
  fetchRoomDetail(room)
  updatePlayers(room.roomPlayers)
  updateRoomGames(room.roomGames)
}

const handleError = (err) => {
  console.error(err)
  alert(err.response.data ? err.response.data.message : '알 수 없는 예외가 발생했습니다.')

  router.replace('/rooms')
}

const toggleReady = (memberId) => {
  const request = {
    memberId: memberId,
  }

  roomSocket.ready(currentRoom.value.id, request)
}

const handleExit = async (memberId, nickname) => {
  const request = {
    memberId: memberId,
    nickname: nickname,
  }

  isJustOver.value = false
  await roomSocket.exit(currentRoom.value.id, request)
  router.push({ path: '/rooms' })
}

const handleStart = () => {
  roomSocket.start(currentRoom.value.id)
}

const handleBackToRoom = (memberId) => {
  const request = {
    memberId: memberId,
  }

  isJustOver.value = false

  roomSocket.backRoom(currentRoom.value.id, request)
  validatePlayer()
}
</script>

<style>
#player-list-container-wrapper {
  top: 8%;
}
</style>
