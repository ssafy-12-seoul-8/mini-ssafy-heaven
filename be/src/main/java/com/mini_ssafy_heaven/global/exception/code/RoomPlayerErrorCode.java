package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomPlayerErrorCode {
  NOT_A_MANAGER("해당 방의 방장이 아닙니다."),
  NOT_JOINED("방 참여자가 아닙니다.");

  private final String message;
}
