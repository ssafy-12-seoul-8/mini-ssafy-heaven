package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomErrorCode {
  LOGIN_USER_NOT_EXISTS("존재하지 않는 사용자입니다."),
  JOINED_PLAYER("다른 방에 참여하는 사용자입니다."),
  EMPTY_TITLE("방 제목은 필수입니다."),
  EXCESSIVE_TITLE_LENGTH("방 제목은 최대 20자입니다."),
  POSITIVE_CAPACITY_REQUIRED("방 인원 제한은 양수여야 합니다."),
  EXCESSIVE_CAPACITY("방 인원 제한은 최대 5인입니다."),
  EMPTY_GAMES("선택된 게임이 없습니다."),
  GAME_NOT_AVAILABLE("유효하지 않은 게임이 포함되어 있습니다."),
  UNEXPECTED_EMPTY_ROOM("해당 방이 존재하지 않습니다."),
  FULL_ROOM("방의 정원이 전부 찼습니다."),
  NULL_STATUS_FOR_UPDATE("변경할 상태가 입력되지 않았습니다."),
  NOT_POSSIBLE_TO_ENTER("참여할 수 없는 방입니다."),
  NOT_READY_YET("아직 준비하지 않은 플레이어가 있습니다.");

  private final String message;

}
