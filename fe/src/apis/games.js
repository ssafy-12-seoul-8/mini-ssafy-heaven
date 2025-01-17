import { http } from './instance'

const gameBasePath = 'api/games'

export const gameApi = {
  getAll: () => http.get(gameBasePath),
  getById: (id) => http.get(`${gameBasePath}/${id}`),
}
