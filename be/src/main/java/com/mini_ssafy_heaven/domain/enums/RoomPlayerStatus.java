package com.mini_ssafy_heaven.domain.enums;

import java.util.Arrays;
import java.util.Objects;

public enum RoomPlayerStatus {
  WAITING,
  READY,
  IN_GAME;

  public static RoomPlayerStatus get(String status) {
    return Arrays.stream(RoomPlayerStatus.values())
        .filter(playerStatus -> Objects.equals(status, playerStatus.name()))
        .findFirst()
        .orElse(WAITING);
  }

  public boolean isReady() {
    return this == READY;
  }

}
