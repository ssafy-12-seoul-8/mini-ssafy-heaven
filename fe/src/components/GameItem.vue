<template>
  <div>
    <button
      @click="open"
      class="relative inline-flex items-center justify-center p-0.5 mb-2 me-2 overflow-hidden text-2xl text-gray-900 rounded-lg group bg-gradient-to-br from-cyan-500 to-blue-500 group-hover:from-cyan-500 group-hover:to-blue-500 hover:text-white focus:ring-4 focus:outline-none focus:ring-cyan-200 dark:focus:ring-cyan-800"
    >
      <span
        class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white rounded-md group-hover:bg-opacity-0"
      >
        {{ game.title }}
        <div v-if="game.isSelected" class="text-base">진행라운드 {{ game.round }}</div>
      </span>
    </button>
  </div>
  <ModalGameDescribe
    :is-open="isOpen"
    :game="game"
    @confirm="onConfirm"
    @cancel="onCancel"
    is-creating
  />
</template>

<script setup>
import { ref } from 'vue'
import { useGameStore } from '@/stores/games'
import ModalGameDescribe from './ModalGameDescribe.vue'

const isOpen = ref(false)
const gameStore = useGameStore()
const { select, unselect } = gameStore
const { game } = defineProps({
  game: {
    type: Object,
    required: true,
  },
})

const open = () => {
  isOpen.value = true
}

const onConfirm = (round) => {
  select(game.id, round)

  isOpen.value = false
}

const onCancel = () => {
  if (game.isSelected) {
    unselect(game.id)
  }

  isOpen.value = false
}
</script>
