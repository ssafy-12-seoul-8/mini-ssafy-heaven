<template>
  <div
    id="rooms-container"
    :class="{ empty: isEmpty }"
    class="py-2 flex flex-wrap content-start overflow-y-auto [&::-webkit-scrollbar]:w-1.5 [&::-webkit-scrollbar-track]:rounded-full [&::-webkit-scrollbar-track]:bg-gray-100 [&::-webkit-scrollbar-thumb]:rounded-full [&::-webkit-scrollbar-thumb]:bg-gray-300"
  >
    <RoomCard v-for="room in rooms" :key="room.id" :room="room" />
    <InfiniteLoading
      target="#rooms-container"
      @infinite="load"
      :slots="messages"
      class="w-full flex justify-center"
    />
    <BaseButton type="white" size="w-16" @click="refreshRooms" class="absolute top-4 right-24">
      ⟳
    </BaseButton>
  </div>
</template>

<script setup>
import { ref, onBeforeMount } from 'vue'
import { roomApi } from '@/apis/rooms'
import RoomCard from '@/components/RoomCard.vue'
import { useRoomStore } from '@/stores/rooms'
import { storeToRefs } from 'pinia'
import InfiniteLoading from 'v3-infinite-loading'
import BaseButton from '@/components/BaseButton.vue'

const size = 15
const cursor = ref(0)
const isEmpty = ref(false)
const hasNext = ref(false)
const roomStore = useRoomStore()
const { rooms } = storeToRefs(roomStore)
const { fetchRooms, clearRooms } = roomStore
const messages = ref({
  complete: '마지막 방입니다!',
})

onBeforeMount(() => {
  clearRooms()
})

const refreshRooms = async () => {
  cursor.value = 0
  hasNext.value = true
  isEmpty.value = false
  clearRooms()
  await fetchAllRooms()
}

const load = async ($state) => {
  await fetchAllRooms()

  if (isEmpty.value || !hasNext.value) {
    if (isEmpty.value) {
      messages.value.complete = '등록된 방이 없습니다!'
    }

    $state.complete()

    return
  }

  $state.loaded()
}

const fetchAllRooms = async () => {
  await roomApi
    .getAll(cursor.value, size)
    .then((res) => updateRooms(res.data))
    .catch((err) => console.err(err))
}

const updateRooms = (data) => {
  if (data.isEmpty) {
    isEmpty.value = true

    return
  }

  fetchRooms(data.contents)

  cursor.value = data.nextCursor
  hasNext.value = data.hasNext
}
</script>

<style scoped>
.empty {
  justify-content: center;
  align-items: center;
}

#rooms-container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
}
</style>
