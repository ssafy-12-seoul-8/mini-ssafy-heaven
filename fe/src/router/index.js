import CreateRoomView from '@/views/CreateRoomView.vue'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RoomView from '@/views/RoomView.vue'
import SignUpView from '@/views/SignUpView.vue'
import RoomsView from '@/views/RoomsView.vue'
import GetMeView from '@/views/GetMeView.vue'
import { createRouter, createWebHistory } from 'vue-router'
import { useMemberStore } from '@/stores/members';

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
    meta: { requiresAuth: true },
  },
  {
    path: '/rooms/:id',
    name: 'room',
    component: RoomView,
    meta: { requiresAuth: true },
  },
  {
    path: '/signUp',
    name: 'signUp',
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
    meta: { requiresAuth: true },
  },
  {
    path: '/me',
    name: 'me',
    component: GetMeView,
    meta: { requiresAuth: true },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {

  const memberStore = useMemberStore();
  const isAuthenticated = !!memberStore.me.nickname; // 로그인 여부 확인

  console.log(`Navigating to: ${to.name}, isAuthenticated: ${isAuthenticated}`);

  if (to.meta.requiresAuth && !isAuthenticated) {
    // 인증이 필요한 페이지인데 로그인이 안 되어 있으면
    next({ name: "login" });
  } else if ((to.name === "login" || to.name === "signUp" || to.name === "home") && isAuthenticated) {
    // 로그인 상태에서 로그인 페이지로 접근하면 리다이렉트
    next({ name: "rooms" });
  } else {
    next(); // 그 외에는 정상 진행
  }
});

export default router
