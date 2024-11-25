export const RoomPlayerStatus = {
  WAITING: {
    value: 'WAITING',
    print: '대기 중',
  },
  READY: {
    value: 'READY',
    print: '준비완료',
  },
  IN_GAME: {
    value: 'IN_GAME',
    print: '게임 중',
  },
  isReady: (player) => {
    if (!player) {
      return false
    }

    return player.status === RoomPlayerStatus.READY.value
  },
}
