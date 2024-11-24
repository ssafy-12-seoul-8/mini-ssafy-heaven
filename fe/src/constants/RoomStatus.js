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
  isInput: (name) => name === RoomStatus.INPUT_INFO.name,
  isReady: (name) => name === RoomStatus.READY_FOR_GAME_SELECTION.name,
  isWaiting: (name) => name === RoomStatus.WAITING.name,
}

Object.freeze(RoomStatus)
