package com.mini_ssafy_heaven.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomGame {
  
  private Long id;
  private final Long roomId;
  private final Long gameId;
  private final Integer round;
  
  @Builder
  private RoomGame(Long roomId, Long gameId) {
    this.roomId = roomId;
    this.gameId = gameId;
    this.round = 0;
  }

}
