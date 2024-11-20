package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomGameErrorCode {
  NULL_ROUND_LIMIT("라운드 정보가 입력되지 않았습니다."),
  POSITIVE_ROUND_LIMIT_REQUIRED("라운드 정보는 양수여야 합니다."),
  POSITIVE_CURRENT_ROUND_REQUIRED("현재 라운드는 양수여야 합니다."),
  EXCESSIVE_CURRENT_ROUND("현재 라운드가 최대를 넘었습니다.");

  private final String message;
}
