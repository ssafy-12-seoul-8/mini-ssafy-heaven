package com.mini_ssafy_heaven.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomStatus {
  CREATING("생성 중"),
  WAITING("대기 중"),
  PLAYING("게임 중");

  private final String status;
}
