<template>
  <div class="h-full w-full">
    <ChatBox />
    <div id="player-list-container-wrapper" class="w-screen h-5/6 absolute left-0">
      <PlayerList :room-players="dummyPlayers" />
    </div>
  </div>
</template>

<script setup>
import PlayerList from '@/components/PlayerList.vue'
import ChatBox from '@/components/ChatBox.vue'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'

const roomPlayerStore = useRoomPlayerStore()
const { dummyPlayers } = storeToRefs(roomPlayerStore)
</script>

<style>
#player-list-container-wrapper {
  top: 8%;
}
</style>
