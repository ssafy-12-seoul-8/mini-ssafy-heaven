package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.domain.enums.RoomPlayerStatus;
import lombok.Builder;

@Builder
public record ReadyResponse(
    Long memberId, RoomPlayerStatus status, Integer currentReadyCount, Integer totalCount
) {

  public static ReadyResponse from(RoomPlayer roomPlayer, Integer currentReadyCount, Integer totalCount) {
    return ReadyResponse.builder()
        .memberId(roomPlayer.getMemberId())
        .status(roomPlayer.getStatus())
        .currentReadyCount(currentReadyCount)
        .totalCount(totalCount)
        .build();
  }

}
