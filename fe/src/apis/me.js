import { http } from './instance'

const ME_PREFIX = 'api/members'

export const meApi = {
    me: (body) => http.get(`${ME_PREFIX}/me`, body),
}