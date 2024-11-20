package com.mini_ssafy_heaven.domain.enums;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomPlayerRole {
  MANAGER("방장"),
  PLAYER("플레이어");

  private final String role;

  public static RoomPlayerRole get(String role) {
    return Arrays.stream(RoomPlayerRole.values())
        .filter(playerRole -> Objects.equals(role, playerRole.role))
        .findFirst()
        .orElse(PLAYER);
  }

}
