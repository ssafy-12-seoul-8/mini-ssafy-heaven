package com.mini_ssafy_heaven.fixture;

import com.mini_ssafy_heaven.domain.RoomGame;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoomGameFixture extends BaseFixture {

  public static RoomGame createWithRoomAndGame(
      Long roomId,
      Long gameId
  ) {
    return createRandom(roomId, gameId, null, null);
  }

  public static RoomGame createRandom(
      Long roomId,
      Long gameId,
      Integer currentRound,
      Integer roundLimit
  ) {
    if (Objects.isNull(roundLimit)) {
      roundLimit = getRandomInt(3, 10);
    }

    if (Objects.nonNull(currentRound) && currentRound > roundLimit) {
      currentRound = getRandomInt(1, roundLimit);
    }

    return create(roomId, gameId, currentRound, roundLimit);
  }

  public static RoomGame create(
      Long roomId,
      Long gameId,
      Integer currentRound,
      Integer roundLimit
  ) {
    return RoomGame.builder()
        .roomId(roomId)
        .gameId(gameId)
        .currentRound(currentRound)
        .roundLimit(roundLimit)
        .build();
  }

}
