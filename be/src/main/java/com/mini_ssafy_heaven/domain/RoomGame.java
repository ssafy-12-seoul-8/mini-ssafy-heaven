package com.mini_ssafy_heaven.domain;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomGame {

  private final Long id;
  private final Long roomId;
  private final Long gameId;
  private final Integer currentRound;
  private final Integer roundLimit;

  @Builder
  private RoomGame(
      Long id,
      Long roomId,
      Long gameId,
      Integer currentRound,
      Integer roundLimit
  ) {
    this.id = id;
    this.roomId = roomId;
    this.gameId = gameId;
    this.currentRound = Objects.nonNull(currentRound) ? currentRound : 0;
    this.roundLimit = Objects.nonNull(roundLimit) ? roundLimit: 0;
  }

}
