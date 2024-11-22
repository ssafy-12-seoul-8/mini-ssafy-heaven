<template>
  <div class="flex flex-col gap-4 justify-center items-center">
    <span class="text-3xl">시작 대기 중입니다...</span>
    <span class="text-xl"
      >준비 인원: {{ currentRoom.currentReadyCount }} / {{ currentRoom.totalCount }}</span
    >
  </div>
</template>

<script setup>
import { useRoomStore } from '@/stores/rooms'
import { storeToRefs } from 'pinia'

const roomStore = useRoomStore()
const { currentRoom } = storeToRefs(roomStore)
</script>
