package com.mini_ssafy_heaven.domain.enums;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomStatus {
  CREATING("생성 중"),
  WAITING("대기 중"),
  PLAYING("게임 중");

  private final String status;

  public static RoomStatus get(String status) {
    return Arrays.stream(RoomStatus.values())
        .filter(roomStatus -> Objects.equals(status, roomStatus.name()))
        .findFirst()
        .orElse(CREATING);
  }

  public boolean isWaiting() {
    return this == WAITING;
  }

}
