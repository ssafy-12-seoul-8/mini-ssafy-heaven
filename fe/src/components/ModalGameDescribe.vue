<template>
  <div
    id="game-describe-modal-wrapper"
    class="fixed left-0 top-0 w-full h-full bg-black/40 z-50"
    v-if="isOpen"
    @click="$emit('cancel')"
  >
    <div
      id="game-describe-modal-container"
      class="relative left-1/2 top-1/2 w-1/3 bg-white rounded-lg p-5"
      @click.stop=""
    >
      <div id="game-contents-wrapper" class="flex flex-col justify-center items-center mb-5">
        <div class="p-4 border-b-4 w-4/5 text-center">{{ game.title }}</div>
        <div class="p-4 text-lg">{{ game.description }}</div>
        <div v-if="isCreating" id="round-wrapper" class="flex justify-center items-center text-lg">
          <span>진행 라운드</span>
          <span>
            <input
              class="w-10 text-center"
              type="number"
              min="0"
              :value="round"
              @change="(e) => (round = e.target.value)"
            />
            / {{ game.maxRound }}
          </span>
        </div>
        <span class="text-red-700 text-lg">{{ roundWarnMessage }}</span>
      </div>
      <!-- TODO: 버튼 구현 후 교체 -->
      <div id="modal-button-wrapper" class="flex justify-center items-center gap-4 text-lg">
        <BaseButton @click="onConfirm" size="w-32">{{
          game.isSelected ? '수정하기' : '추가하기'
        }}</BaseButton>
        <BaseButton @click="$emit('cancel')" type="white" size="w-32">{{
          game.isSelected ? '취소하기' : '돌아가기'
        }}</BaseButton>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import BaseButton from './BaseButton.vue'

const round = ref(0)
const roundWarnMessage = ref()

const { isOpen, game, isCreating } = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  game: {
    type: Object,
    required: true,
  },
  isCreating: {
    type: Boolean,
    default: false,
  },
})

const emit = defineEmits(['confirm', 'cancel'])

const onConfirm = () => {
  if (!validateRound(round.value)) {
    return
  }

  emit('confirm', round.value)
}

const validateRound = (round) => {
  if (round == 0) {
    roundWarnMessage.value = '라운드가 설정되지 않았습니다!'

    return false
  }

  if (round > game.maxRound) {
    roundWarnMessage.value = `라운드는 최대 ${game.maxRound}라운드까지 가능합니다!`

    return false
  }

  roundWarnMessage.value = ''

  return true
}

onMounted(() => {
  if (game.round) {
    round.value = game.round
  }
})
</script>

<style>
#game-describe-modal-container {
  transform: translate(-50%, -50%);
}
</style>
