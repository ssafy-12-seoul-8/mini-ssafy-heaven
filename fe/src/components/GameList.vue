<template>
  <div id="game-list" class="flex flex-col gap-4">
    <div class="text-center">{{ title }}</div>
    <div
      id="game-items-wrapper"
      class="grow flex flex-col justify-center items-center min-w-80 min-h-80 rounded-md border-2 bg-white border-main-skyblue"
    >
      <GameItem v-for="game in games" :key="game.id" :game="game" />
    </div>
  </div>
</template>

<script setup>
import GameItem from './GameItem.vue'

const { title, games } = defineProps({
  title: {
    type: String,
    required: true,
  },
  games: {
    type: Array,
    required: true,
  },
})
</script>
