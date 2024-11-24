<template>
  <div class="grow flex justify-center items-center">
    <RoomWaiting v-if="isWaiting" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoomStore } from '@/stores/rooms'
import RoomWaiting from './RoomWaiting.vue'
import { storeToRefs } from 'pinia'
import { RoomStatus } from '@/constants/RoomStatus'

const roomStore = useRoomStore()
const { currentRoom } = storeToRefs(roomStore)
const isWaiting = computed(() => RoomStatus.isWaiting(currentRoom.value.status))
</script>
