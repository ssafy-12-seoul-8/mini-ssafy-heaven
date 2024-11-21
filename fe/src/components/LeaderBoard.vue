<template>
  <div
    id="leader-board-container"
    class="flex flex-col justify-center items-center rounded-lg shadow-lg bg-white"
  >
    <RoomInfo :room="currentRoom" class="mb-2 mt-2" />
    <div class="flex flex-col justify-center items-center mb-4">
      <TransitionGroup name="leader-board" move-class="leaderboard__item--move">
        <LeaderBoardItem v-for="player in sortedByRank" :player="player" :key="player.nickname" />
      </TransitionGroup>
    </div>
  </div>
</template>

<script setup>
import { useRoomStore } from '@/stores/rooms'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import LeaderBoardItem from './LeaderBoardItem.vue'
import { storeToRefs } from 'pinia'
import RoomInfo from './RoomInfo.vue'

const roomPlayerStore = useRoomPlayerStore()
const roomStore = useRoomStore()
const { sortedByRank } = storeToRefs(roomPlayerStore)
const { currentRoom } = storeToRefs(roomStore)
</script>

<style>
.leaderboard__item--move {
  transition: transform 0.2s;
}
</style>
