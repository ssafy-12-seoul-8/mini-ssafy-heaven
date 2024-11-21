package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.global.exception.code.RoomGameErrorCode;
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
    validate(currentRound, roundLimit);

    this.id = id;
    this.roomId = roomId;
    this.gameId = gameId;
    this.currentRound = Objects.nonNull(currentRound) ? currentRound : 1;
    this.roundLimit = roundLimit;
  }

  private void validate(Integer currentRound, Integer roundLimit) {
    validateRoundLimit(roundLimit);
    validateCurrentRound(currentRound, roundLimit);
  }

  private void validateRoundLimit(Integer roundLimit) {
    if (Objects.isNull(roundLimit)) {
      throw new IllegalArgumentException(RoomGameErrorCode.NULL_ROUND_LIMIT.getMessage());
    }

    if (roundLimit <= 0) {
      throw new IllegalArgumentException(
          RoomGameErrorCode.POSITIVE_ROUND_LIMIT_REQUIRED.getMessage());
    }
  }

  private void validateCurrentRound(
      Integer currentRound,
      Integer roundLimit
  ) {
    if (Objects.isNull(currentRound)) {
      return;
    }

    if (currentRound <= 0) {
      throw new IllegalArgumentException(
          RoomGameErrorCode.POSITIVE_CURRENT_ROUND_REQUIRED.getMessage());
    }

    if (currentRound > roundLimit) {
      throw new IllegalArgumentException(RoomGameErrorCode.EXCESSIVE_CURRENT_ROUND.getMessage());
    }
  }

}
