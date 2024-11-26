<template>
  <div class="grow flex justify-center items-center">
    <RoomWaiting v-if="isWaiting" />
    <RoomPlaying v-if="isPlaying" />
  </div>
</template>

<script setup>
import { useRoomStore } from '@/stores/rooms'
import RoomWaiting from './RoomWaiting.vue'
import { storeToRefs } from 'pinia'
import RoomPlaying from './RoomPlaying.vue'

const roomStore = useRoomStore()
const { isPlaying, isWaiting } = storeToRefs(roomStore)
</script>
