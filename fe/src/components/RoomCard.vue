<template>
  <div class="flex w-1/2 h-1/6 justify-center items-center">
    <div
      id="room-card-wrapper"
      class="flex border rounded-lg border-slate-300 w-11/12 hover:scale-105 transition cursor-pointer"
      @click="join"
    >
      <div
        id="room-id-container"
        class="flex flex-col justify-center items-center w-1/5 border rounded-l-lg"
        :class="bgColor"
      >
        <span>{{ room.id }}</span>
        <span>{{ status }}</span>
      </div>
      <div id="room-contents-container" class="flex justify-between mx-4 grow items-center">
        <div id="room-info" class="flex flex-col justify-center">
          <span class="text-2xl">{{ room.title }}</span>
          <span class="text-sm">방장: {{ room.manager }}</span>
          <div id="room-games-container" class="flex border border-main-skyblue rounded-lg w-fit">
            <span v-for="game in room.roomGames" :key="game" class="text-xs px-1">{{
              game.title
            }}</span>
          </div>
        </div>
        <div id="capacity-container">{{ room.currentPlayerCount }} / {{ room.capacity }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { roomApi, roomSocket } from '@/apis/rooms'
import { RoomStatus } from '@/constants/RoomStatus'
import { useMemberStore } from '@/stores/members'
import { storeToRefs } from 'pinia'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const status = ref()
const bgColor = ref('bg-slay-300')
const router = useRouter()
const memberStore = useMemberStore()
const { myNickname } = storeToRefs(memberStore)

const { room } = defineProps({
  room: {
    type: Object,
    required: true,
  },
})

onMounted(() => {
  if (room.status === RoomStatus.CREATING.name) {
    bgColor.value = 'bg-lime-500'
  } else if (room.status === RoomStatus.WAITING.name) {
    bgColor.value = 'bg-main-skyblue'
  }

  status.value = RoomStatus.findMessage(room.status)
})

const join = () => {
  roomApi
    .join(room.id)
    .then(() => enter())
    .then(() => router.replace({ name: 'room', params: { id: room.id } }))
    .catch((err) => alert(err.response.data.message))
}

const enter = () => {
  const request = {
    nickname: myNickname.value,
  }

  roomSocket.enter(room.id, request)
}
</script>

<style scoped>
#room-card-wrapper {
  height: 90%;
}
</style>
