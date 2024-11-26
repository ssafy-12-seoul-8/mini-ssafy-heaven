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
      <div class="grow flex flex-col justify-between items-center">
        <LeaderBoard class="w-11/12" />
        <div class="flex flex-col gap-4 justify-center items-center">
          <BaseButton v-if="isJustOver" @click="onClickToRoom">방으로</BaseButton>
          <BaseButton v-if="isJustOver" @click="onClickExit" type="white">나가기</BaseButton>
          <BaseButton v-if="!isPlaying" @click="onClickReady" :disabled="startDisabled">{{
            readyButtonText
          }}</BaseButton>
          <BaseButton v-if="!isPlaying" @click="onClickExit" type="white" :disabled="isPlaying"
            >나가기</BaseButton
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import LeaderBoard from './LeaderBoard.vue'
import PlayerCard from './PlayerCard.vue'
import BaseButton from './BaseButton.vue'
import { useRoomPlayerStore } from '@/stores/roomPlayers'
import { storeToRefs } from 'pinia'
import { RoomPlayerStatus } from '@/enums/RoomPlayerStatus'
import { useRoomStore } from '@/stores/rooms'

const roomStore = useRoomStore()
const roomPlayerStore = useRoomPlayerStore()
const { isPossibleToStart, isPlaying, isJustOver } = storeToRefs(roomStore)
const { roomPlayers, currentPlayer, manager } = storeToRefs(roomPlayerStore)
const isReady = computed(() => RoomPlayerStatus.isReady(currentPlayer.value))
const isManager = computed(
  () => manager.value && manager.value.memberId === currentPlayer.value.memberId,
)
const startDisabled = computed(() => isManager.value && !isPossibleToStart.value)
const readyButtonText = computed(() => {
  if (isManager.value) {
    return isPossibleToStart.value ? '시작하기' : '대기 중'
  }

  return isReady.value ? '취소' : '준비'
})

const emit = defineEmits(['ready', 'exit', 'start', 'toRoom'])

const isInLeft = (index) => {
  return index === 0 || index === 2 || index === 4
}

const onClickReady = () => {
  if (isManager.value && isPossibleToStart.value) {
    emit('start')

    return
  }

  emit('ready', currentPlayer.value.memberId)
}

const onClickExit = () => {
  emit('exit', currentPlayer.value.memberId, currentPlayer.value.nickname)
}

const onClickToRoom = () => {
  emit('toRoom', currentPlayer.value.memberId)
}
</script>

<style>
.player-card-container-shown {
  height: 26%;
}
</style>
