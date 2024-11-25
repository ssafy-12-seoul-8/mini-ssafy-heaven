import { Baseball } from './Baseball'

export const GameType = {
  BASEBALL: {
    value: 'BASEBALL',
    instance: Baseball,
  },
}

export const findGameType = (value) => {
  return Object.entries(GameType).find((entry) => entry[1].value === value)[1]
}

export const GameMessageType = {
  START: 'START',
}

export const findGameMessageType = (value) => {
  return Object.entries(GameMessageType).find((entry) => entry[1] === value)[1]
}

Object.freeze(GameMessageType)
