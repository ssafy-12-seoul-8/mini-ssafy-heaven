import CreateRoomView from '@/views/CreateRoomView.vue'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RoomView from '@/views/RoomView.vue'
import SignUpView from '@/views/SignUpView.vue'
import RoomsView from '@/views/RoomsView.vue'
import GetMeView from '@/views/GetMeView.vue'
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
  {
    path: '/signUp',
    name: 'singUp',
    component: SignUpView,
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
  },
  {
    path: '/rooms',
    name: 'rooms',
    component: RoomsView,
  },
  {
    path: '/me',
    name: 'me',
    component: GetMeView,
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})

export default router
