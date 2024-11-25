package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InGameErrorCode {
  NO_GAME_FOR_ROOM("해당 방의 진행 중인 게임이 없습니다.");

  private final String message;
}
