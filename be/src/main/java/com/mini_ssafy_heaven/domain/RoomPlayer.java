package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.RoomPlayerRole;
import java.util.Objects;
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
  private RoomPlayer(Long id, Long memberId, Long roomId, RoomPlayerRole role, Integer score) {
    this.id = id;
    this.memberId = memberId;
    this.roomId = roomId;
    this.score = Objects.isNull(score) ? 0 : score;
    this.role = Objects.isNull(role) ? RoomPlayerRole.PLAYER : role;
  }

  public static RoomPlayer createManager(Long memberId, Long roomId) {
    return RoomPlayer.builder()
        .memberId(memberId)
        .roomId(roomId)
        .role(RoomPlayerRole.MANAGER)
        .build();
  }

  public static RoomPlayer createPlayer(Long memberId, Long roomId) {
    return RoomPlayer.builder()
        .memberId(memberId)
        .roomId(roomId)
        .build();
  }

  public boolean hasSameId(Long roomPlayerId) {
    return this.id.equals(roomPlayerId);
  }

  public boolean isManager() {
    return role == RoomPlayerRole.MANAGER;
  }

}
