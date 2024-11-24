<template>
  <div class="grow flex justify-center items-center">
    <RoomWaiting v-if="isWaiting" />
    <RoomPlaying v-if="isPlaying" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoomStore } from '@/stores/rooms'
import RoomWaiting from './RoomWaiting.vue'
import { storeToRefs } from 'pinia'
import { RoomStatus } from '@/constants/RoomStatus'
import RoomPlaying from './RoomPlaying.vue'

const roomStore = useRoomStore()
const { currentRoom, isPlaying } = storeToRefs(roomStore)
const isWaiting = computed(() => RoomStatus.isWaiting(currentRoom.value.status))
</script>
