import CreateRoomView from '@/views/CreateRoomView.vue'
import HomeView from '@/views/HomeView.vue'
import RoomView from '@/views/RoomView.vue'
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/rooms/new',
    name: 'createRoom',
    component: CreateRoomView,
  },
  {
    path: '/rooms/:id',
    name: 'room',
    component: RoomView,
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
