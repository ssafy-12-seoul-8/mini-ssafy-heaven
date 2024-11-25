package com.mini_ssafy_heaven.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ROOM-GAME")
@RequiredArgsConstructor
@Builder
public class RoomGameScore {

  @Id
  private final String id;
  private final int score;

  public static RoomGameScore from(RoomPlayer roomPlayer) {
    String roomKey = "ROOM:" + roomPlayer.getRoomId();
    String memberKey = "MEMBER:" + roomPlayer.getMemberId();

    return RoomGameScore.builder()
        .id(roomKey + "-" + memberKey)
        .score(0)
        .build();
  }

}
