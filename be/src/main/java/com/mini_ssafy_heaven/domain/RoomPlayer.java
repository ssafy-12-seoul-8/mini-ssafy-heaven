package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.RoomPlayerRole;
import com.mini_ssafy_heaven.domain.enums.RoomPlayerStatus;
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
  private final RoomPlayerStatus status;

  private RoomPlayer(
    Long id,
    Long memberId,
    Long roomId,
    Integer score,
    RoomPlayerRole role,
    RoomPlayerStatus status
  ) {
    this(id, memberId, roomId, score, role, null, status, null);
  }

  @Builder
  private RoomPlayer(
    Long id,
    Long memberId,
    Long roomId,
    Integer score,
    RoomPlayerRole role,
    String roleString,
    RoomPlayerStatus status,
    String statusString
  ) {
    this.id = id;
    this.memberId = memberId;
    this.roomId = roomId;
    this.score = Objects.isNull(score) ? 0 : score;
    this.role = Objects.isNull(role) ? RoomPlayerRole.get(roleString) : role;
    this.status = Objects.isNull(status) ? RoomPlayerStatus.get(statusString) : status;
  }

  public static RoomPlayer createManager(Long memberId, Long roomId) {
    return RoomPlayer.builder()
        .memberId(memberId)
        .roomId(roomId)
        .role(RoomPlayerRole.MANAGER)
        .status(RoomPlayerStatus.READY)
        .build();
  }

  public static RoomPlayer createPlayer(Long memberId, Long roomId) {
    return RoomPlayer.builder()
        .memberId(memberId)
        .roomId(roomId)
        .build();
  }

  public boolean isSameUser(Long memberId) {
    return Objects.equals(this.memberId, memberId);
  }

  public boolean isManager() {
    return role == RoomPlayerRole.MANAGER;
  }

  public boolean isReady() {
    return status.isReady();
  }

  public RoomPlayer toggleReady() {
    return update(this.role, this.score, status.isWaiting() ? RoomPlayerStatus.READY : RoomPlayerStatus.WAITING);
  }

  public RoomPlayer promoteToManager() {
    return update(RoomPlayerRole.MANAGER, this.score, RoomPlayerStatus.READY);
  }

  public RoomPlayer startGame() {
    return update(this.role, this.score, RoomPlayerStatus.IN_GAME);
  }

  public RoomPlayer earnScore(int score) {
    return update(this.role, this.score + score, this.status);
  }

  public RoomPlayer initializeScore() {
    return update(this.role, 0, this.status);
  }

  private RoomPlayer update(RoomPlayerRole role, int score, RoomPlayerStatus status) {
    return RoomPlayer.builder()
        .id(id)
        .memberId(memberId)
        .roomId(roomId)
        .role(role)
        .score(score)
        .status(status)
        .build();
  }

}
