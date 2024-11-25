import { http } from './instance'

const MEMBER_PREFIX = 'api/members'

export const memberApi = {
  signup: (body) => http.post(`${MEMBER_PREFIX}/signUp`, body),
  login: (body) => http.post(`${MEMBER_PREFIX}/login`, body),
  getDetail: () => http.get(`${MEMBER_PREFIX}/me`),
  loginGuest: () => http.post(`${MEMBER_PREFIX}/login/guest`),
  logout: () => http.post(`${MEMBER_PREFIX}/logout`),
}
