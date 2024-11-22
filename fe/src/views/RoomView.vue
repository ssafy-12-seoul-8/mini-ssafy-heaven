<template>
  <div class="h-full w-full">
    <ChatBox />
    <div id="player-list-container-wrapper" class="w-screen h-5/6 absolute left-0">
      <PlayerList :room-players="roomPlayers" />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import PlayerList from '@/components/PlayerList.vue'
import ChatBox from '@/components/ChatBox.vue'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'
import { roomApi } from '@/apis/rooms'
import { useRoute } from 'vue-router'
import { useRoomStore } from '@/stores/rooms'
import { useRouter } from 'vue-router'

const router = useRouter()
const { params } = useRoute()
const roomPlayerStore = useRoomPlayerStore()
const roomStore = useRoomStore()
const { roomPlayers } = storeToRefs(roomPlayerStore)
const { updatePlayers } = roomPlayerStore
const { fetchRoomDetail } = roomStore

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

onMounted(() => {
  validatePlayer()
})
</script>

<style>
#player-list-container-wrapper {
  top: 8%;
}
</style>
