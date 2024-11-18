package com.mini_ssafy_heaven.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomPlayerRole {
  MANAGER("방장"),
  PLAYER("플레이어");

  private final String role;

}
