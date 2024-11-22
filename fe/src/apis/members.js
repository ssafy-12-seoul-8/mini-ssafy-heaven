import { http } from './instance'

const MEMBER_PREFIX = 'api/members'

export const memberApi = {
  signup: (body) => http.post(`${MEMBER_PREFIX}/signUp`, body),
  loginGuest: () => http.post(`${MEMBER_PREFIX}/login/guest`),
}
