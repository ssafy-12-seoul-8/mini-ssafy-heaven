package com.mini_ssafy_heaven.dto.query;

import com.mini_ssafy_heaven.domain.enums.RoomPlayerStatus;
import lombok.Builder;

@Builder
public record RoomPlayerNameDto(
    Long memberId,
    String nickname,
    Integer roomScore,
    Integer score,
    Integer rank,
    RoomPlayerStatus status
) {

}
