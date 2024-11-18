package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.RoomPlayerRole;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomPlayer {

  private final Long id;
  private final Long memberId;
  private final Long roomId;
  private final Integer score;
  private final RoomPlayerRole role;

  @Builder
  private RoomPlayer(
      Long id,
      Long memberId,
      Long roomId,
      RoomPlayerRole role,
      String roleString
  ) {
    this.id = id;
    this.memberId = memberId;
    this.roomId = roomId;
    this.score = 0;
    this.role = Objects.isNull(role) ? RoomPlayerRole.get(roleString) : role;
  }

  public static RoomPlayer createManager(Long memberId, Long roomId) {
    return RoomPlayer.builder()
        .memberId(memberId)
        .roomId(roomId)
        .role(RoomPlayerRole.MANAGER)
        .build();
  }

}
