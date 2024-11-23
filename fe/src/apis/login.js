import { http } from './instance'

const LOGIN_PREFIX = 'api/members'

export const loginApi = {
  login: (body) => http.post(`${LOGIN_PREFIX}/login`, body),
}
