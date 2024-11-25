package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GameErrorCode {
  GAME_NOT_FOUND("해당 아이디의 게임이 없습니다");

  private final String message;
}
