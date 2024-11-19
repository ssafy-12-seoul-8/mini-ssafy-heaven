<template>
  <div id="select-games-container" class="flex flex-col justify-center items-center text-2xl">
    <div class="mb-4">게임 선택</div>
    <div class="flex gap-10 flex-wrap justify-center items-center">
      <GameList title="게임 종류" :games="unSelectedGames" />
      <GameList title="선택된 게임" :games="selectedGames" />
    </div>
    <div class="w-full flex justify-end">{{ selectedGames?.length }} / 3</div>
    <div id="create-room-button-wrapper" class="flex gap-8 mt-4">
      <button @click="$emit('moveBack')">이전으로</button>
      <button @click="$emit('submit')">방 만들기</button>
    </div>
  </div>
</template>

<script setup>
import GameList from './GameList.vue'
import { useGameStore } from '@/stores/games'
import { storeToRefs } from 'pinia'

const gameStore = useGameStore()
const { unSelectedGames, selectedGames } = storeToRefs(gameStore)

defineEmits(['moveBack', 'submit'])
</script>
