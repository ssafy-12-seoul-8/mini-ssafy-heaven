<template>
  <div id="player-list-container" class="flex justify-between w-full h-full">
    <div id="player-list-left" class="flex flex-col w-1/5 flex-start gap-4 mt-2">
      <div
        :class="{ 'player-card-container-shown': isInLeft(index) }"
        v-for="(player, index) in roomPlayers"
        :key="player.nickname"
        class="w-11/12 ml-4"
      >
        <PlayerCard v-if="isInLeft(index)" :player="player" class="w-11/12 ml-2" />
      </div>
    </div>
    <div id="player-list-right" class="flex flex-col w-1/5 flex-start gap-4">
      <div
        :class="{ 'player-card-container-shown': !isInLeft(index) }"
        v-for="(player, index) in roomPlayers"
        :key="player.nickname"
        class="w-11/12 mr-4"
      >
        <PlayerCard v-if="!isInLeft(index)" :player="player" />
      </div>
      <div class="grow flex flex-col justify-between">
        <LeaderBoard />
        <button class="mb-8">{{ isReady ? '취소' : '준비' }}</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import LeaderBoard from './LeaderBoard.vue'
import PlayerCard from './PlayerCard.vue'

const isReady = ref(false)
const { roomPlayers } = defineProps({
  roomPlayers: {
    type: Array,
    required: true,
  },
})

const isInLeft = (index) => {
  return index === 0 || index === 2 || index === 4
}
</script>

<style>
.player-card-container-shown {
  height: 26%;
}
</style>
