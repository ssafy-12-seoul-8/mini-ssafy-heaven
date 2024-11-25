package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GameTypeErrorCode {
  NO_SUCH_GAME_TYPE("해당 이름의 게임을 찾을 수 없습니다");

  private final String message;

}
