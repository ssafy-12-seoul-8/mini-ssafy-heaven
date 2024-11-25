<template>
  <div class="h-5/6 flex flex-col">
    <span v-if="getCurrentGame" class="text-center text-xl">{{ getCurrentGame.title }}</span>
    <div class="grow flex flex-col gap-4 justify-center items-center">
      {{ content }}
    </div>
  </div>
</template>

<script setup>
import { useBaseballStore } from '@/stores/baseball'
import { useRoomGameStore } from '@/stores/roomGames'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'
import { ref, computed, watchEffect } from 'vue'

const content = ref()
const isTagger = computed(() => currentPlayer.value.memberId === tagger.value.memberId)
const roomGameStore = useRoomGameStore()
const roomPlayerStore = useRoomPlayerStore()
const baseballStore = useBaseballStore()
const { getCurrentGame } = storeToRefs(roomGameStore)
const { currentPlayer } = storeToRefs(roomPlayerStore)
const { tagger, normalText, taggerText } = storeToRefs(baseballStore)

watchEffect(() => {
  if (!getCurrentGame.value) {
    content.value = '게임을 시작합니다!'

    return
  }

  if (getCurrentGame.value.title === '숫자야구') {
    if (!normalText.value) {
      content.value = '게임을 시작합니다!'

      return
    }

    content.value = isTagger.value ? taggerText.value : normalText.value
  }
})
</script>
