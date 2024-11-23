import { http } from './instance'

const MEMBER_PREFIX = 'api/members'

export const memberApi = {
  signup: (body) => http.post(`${MEMBER_PREFIX}/signUp`, body),
}

export const loginApi = {
  login: (body) => http.post(`${MEMBER_PREFIX}/login`, body),
}

export const meApi = {
  getDetail: () => http.get(`${MEMBER_PREFIX}/me`),
}
