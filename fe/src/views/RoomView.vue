<template>
  <div class="h-full w-full">
    <div class="h-full w-full flex flex-col justify-between">
      <RoomContents />
      <ChatBox :player="currentPlayer" />
    </div>
    <div id="player-list-container-wrapper" class="w-screen h-5/6 absolute left-0">
      <PlayerList @ready="toggleReady" @exit="handleExit" />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
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

const router = useRouter()
const { params } = useRoute()
const roomPlayerStore = useRoomPlayerStore()
const roomStore = useRoomStore()
const chatStore = useChatStore()
const { currentRoom } = storeToRefs(roomStore)
const { currentPlayer } = storeToRefs(roomPlayerStore)
const { updatePlayers } = roomPlayerStore
const { fetchRoomDetail } = roomStore
const { clearChats } = chatStore

onMounted(() => {
  validatePlayer()

  if (!roomSocket.connected()) {
    roomSocket.enter(params.id)
  }
})

const validatePlayer = () => {
  roomApi
    .getDetail(params.id)
    .then((res) => fetchRoomAndPlayers(res.data))
    .catch((err) => handleError(err))
}

const fetchRoomAndPlayers = (room) => {
  fetchRoomDetail(room)
  updatePlayers(room.roomPlayers)
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

  await roomSocket.exit(currentRoom.value.id, request)
  clearChats()
  router.push({ path: '/rooms' })
}
</script>

<style>
#player-list-container-wrapper {
  top: 8%;
}
</style>
