<template>
  <div id="create-room-view" class="h-full flex flex-col items-center justify-center overflow-auto">
    <span v-if="failMessage" class="text-red-700 text-lg">{{ failMessage }}</span>
    <CreateRoomInput v-show="isWaitingForInput" :roomInfo="roomInfo" @getReady="getReady" />
    <SelectGamesContainer v-show="isReadyForGameSet" @moveBack="unReady" @submit="dispatchCreate" />
    <LoaderBox v-if="loaderOpen" title="입력 완료!" :message="status.message" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import CreateRoomInput from '@/components/CreateRoomInput.vue'
import SelectGamesContainer from '@/components/SelectGamesContainer.vue'
import { isInput, isReady, RoomStatus } from '@/constants/RoomStatus'
import { useGameStore } from '@/stores/games'
import { storeToRefs } from 'pinia'
import { roomApi, roomSocket } from '@/apis/rooms'
import LoaderBox from '@/components/LoaderBox.vue'
import { useRouter } from 'vue-router'

const roomInfo = ref({
  title: '',
  capacity: 0,
})
const status = ref(RoomStatus.INPUT_INFO)
const loaderOpen = ref(false)
const failMessage = ref()
const isWaitingForInput = computed(() => isInput(status.value.name))
const isReadyForGameSet = computed(() => isReady(status.value.name))
const gameStore = useGameStore()
const { fetchAllGames } = gameStore
const { selectedGames } = storeToRefs(gameStore)
const router = useRouter()

onMounted(() => {
  fetchAllGames()
})

onUnmounted(() => {
  if (status.value.name !== RoomStatus.WAITING.name) {
    // TODO: 방 삭제 이후 fetch 추가
    console.log('방 삭제')
  }
})

const getReady = (title, capacity) => {
  setRoomInfo(title, capacity)

  status.value = RoomStatus.READY_FOR_GAME_SELECTION
}

const setRoomInfo = (title, capacity) => {
  roomInfo.value.title = title
  roomInfo.value.capacity = capacity
}

const unReady = () => {
  status.value = RoomStatus.INPUT_INFO
}

const dispatchCreate = () => {
  status.value = RoomStatus.CREATING
  loaderOpen.value = true
  const roomGames = selectedGames.value.map((game) => ({ id: game.id, maxRound: game.round }))
  const request = {
    title: roomInfo.value.title,
    capacity: roomInfo.value.capacity,
    roomGames: roomGames,
  }

  roomApi
    .create(request)
    .then((res) => createRoom(res.data.id))
    .then(() => joinRoom())
    .catch((err) => handleFailCreate(err.response.data.message))
}

const createRoom = (id) => {
  roomInfo.value.id = id
  status.value = RoomStatus.ENTERING
}

const joinRoom = () => {
  roomSocket.enter(roomInfo.value.id, onEnter)
}

const onEnter = () => {
  status.value = RoomStatus.WAITING
  router.replace({ name: 'room', params: { id: roomInfo.value.id } })
}

const handleFailCreate = (message) => {
  failMessage.value = message
  loaderOpen.value = false
  status.value = RoomStatus.READY_FOR_GAME_SELECTION
}
</script>

<style>
#create-room-view {
  align-items: safe center;
  -ms-overflow-style: none; /* IE and Edge */
}

#create-room-view::-webkit-scrollbar {
  display: none;
}
</style>
