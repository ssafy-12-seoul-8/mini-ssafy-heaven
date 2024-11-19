export const RoomStatus = {
  INPUT_INFO: {
    name: 'INPUT_INFO',
    message: '입력 중',
  },
  READY_FOR_GAME_SELECTION: {
    name: 'READY_FOR_GAME_SELECTION',
    message: '게임 설정 중',
  },
  CREATING: {
    name: 'CREATING',
    message: '생성 중 . . .',
  },
  ENTERING: {
    name: 'ENTERING',
    message: '입장 중 . . .',
  },
  WAITING: {
    name: 'WAITING',
    message: '대기 중',
  },
}

export const isInput = (name) => {
  return name === RoomStatus.INPUT_INFO.name
}

export const isReady = (name) => {
  return name === RoomStatus.READY_FOR_GAME_SELECTION.name
}

Object.freeze(RoomStatus)
