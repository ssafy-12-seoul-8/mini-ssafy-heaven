package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.global.exception.code.RoomPlayerCountErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("PLAYER-COUNT")
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class RoomPlayerCount {

  private static final int MAX_PLAYERS = 5;

  @Id
  @EqualsAndHashCode.Include
  private final Long id;
  private final Integer count;

  public RoomPlayerCount increase() {
    return new RoomPlayerCount(id, count + 1);
  }

  public void validateJoinability() {
    if (count >= MAX_PLAYERS) {
      throw new IllegalArgumentException(RoomPlayerCountErrorCode.FULL_ROOM.getMessage());
    }
  }

}
