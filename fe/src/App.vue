<template>
  <div class="relative">
    <BaseButton
      v-if="isRoomsView"
      class="absolute top-4 right-4"
      size="w-16"
      type="white"
      @click="Menu"
      >☰</BaseButton
    >
    <div v-if="menuVisible" class="absolute top-20 right-4 bg-white shadow-md rounded p-2">
      <BaseButton class="block w-full text-left mb-2" @click="me">내 정보</BaseButton>
      <BaseButton class="block w-full text-left mb-2" @click="logout">로그아웃</BaseButton>
      <BaseButton class="block w-full text-left" @click="createRoom">방 만들기</BaseButton>
    </div>
    <Container>
      <router-view />
    </Container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseButton from './components/BaseButton.vue'
import Container from './components/BaseContainer.vue'
import { memberApi } from '@/apis/members'
import { useMemberStore  } from '@/stores/members'

const menuVisible = ref(false)
const route = useRoute()
const router = useRouter()
const memberStore = useMemberStore();

const isRoomsView = computed(() => route.path === '/rooms')
console.log(route.path)

const Menu = () => {
  menuVisible.value = !menuVisible.value
}

const me = () => {
  menuVisible.value = false
  router.push({ path: 'me' })
}

const logout = () => {
  memberApi
    .logout()
    .then(() => {
      memberStore.clearMe();
      moveToMain()
      menuVisible.value = false
    })
    .catch((err) => {
      console.error(err);
      alert('로그아웃이 불가합니다.')
      memberStore.clearMe();
      moveToMain()
      menuVisible.value = false
    })
}

const createRoom = () => {
  menuVisible.value = false
  router.push('/rooms/new')
}

const moveToMain = () => {
  router.replace('/login')
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Poor+Story&display=swap');

#app {
  font-family: 'Poor Story', system-ui;
  font-weight: 400;
  font-style: normal;
}

body {
  background-image: url('/src/assets/BackGroundImg.png');
  background-size: cover;
}
</style>
