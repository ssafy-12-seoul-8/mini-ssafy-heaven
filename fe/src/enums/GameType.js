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
  BEFORE_START: 'BEFORE_START',
  CONFIRM: 'CONFIRM',
  START: 'START',
  SET_ANSWER: 'SET_ANSWER',
  ROUND_START: 'ROUND_START',
  TRY: 'TRY',
  GAME_OVER: 'GAME_OVER',
}

export const findGameMessageType = (value) => {
  return Object.entries(GameMessageType).find((entry) => entry[1] === value)[1]
}

Object.freeze(GameMessageType)
