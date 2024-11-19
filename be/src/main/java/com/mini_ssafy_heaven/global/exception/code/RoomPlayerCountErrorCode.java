package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomPlayerCountErrorCode {
  UNEXPECTED_EMPTY_ROOM("해당 방이 존재하지 않습니다."),
  FULL_ROOM("방의 정원이 전부 찼습니다.");

  private final String message;
}
