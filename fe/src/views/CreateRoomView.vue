<template>
  <div
    id="create-room-view"
    class="h-full flex flex-col items-center justify-center overflow-auto w-11/12"
  >
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
import { RoomStatus } from '@/enums/RoomStatus'
import { useGameStore } from '@/stores/games'
import { storeToRefs } from 'pinia'
import { roomApi, roomSocket } from '@/apis/rooms'
import LoaderBox from '@/components/LoaderBox.vue'
import { useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/members'
import { useRoomStore } from '@/stores/rooms'

const roomInfo = ref({
  title: '',
  capacity: 0,
})
const status = ref(RoomStatus.INPUT_INFO)
const loaderOpen = ref(false)
const failMessage = ref()
const isWaitingForInput = computed(() => RoomStatus.isInput(status.value.name))
const isReadyForGameSet = computed(() => RoomStatus.isReady(status.value.name))
const gameStore = useGameStore()
const roomStore = useRoomStore()
const memberStore = useMemberStore()
const { myNickname } = storeToRefs(memberStore)
const { storeRoomIdInSession } = roomStore
const { fetchAllGames } = gameStore
const { selectedGames } = storeToRefs(gameStore)
const router = useRouter()

onMounted(() => {
  fetchAllGames()
})

onUnmounted(() => {
  if (roomInfo.value.id && status.value.name !== RoomStatus.WAITING.name) {
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
  const roomGames = selectedGames.value.map((game) => ({ id: game.id, roundLimit: game.round }))

  const request = {
    title: roomInfo.value.title,
    capacity: roomInfo.value.capacity,
    roomGames: roomGames,
  }

  roomApi
    .create(request)
    .then((res) => createRoom(res.data.id))
    .then(() => joinRoom())
    .then(() => confirmCreation())
    .then(() => router.push({ name: 'room', params: { id: roomInfo.value.id } }))
    .catch((err) => handleFailCreate(err))
}

const createRoom = (id) => {
  roomInfo.value.id = id
  status.value = RoomStatus.ENTERING
}

const joinRoom = () => {
  const body = {
    nickname: myNickname.value,
  }

  roomSocket.enter(roomInfo.value.id, body)
  storeRoomIdInSession(roomInfo.value.id)
}

const confirmCreation = () => {
  status.value = RoomStatus.WAITING
  const request = {
    status: status.value.name,
  }

  return roomApi.confirmCreation(roomInfo.value.id, request)
}

const handleFailCreate = (error) => {
  console.error(error.respopnse)

  failMessage.value = error.response.data.message
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
