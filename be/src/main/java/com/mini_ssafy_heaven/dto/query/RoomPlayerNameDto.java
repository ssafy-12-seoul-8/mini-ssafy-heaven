package com.mini_ssafy_heaven.dto.query;

public record RoomPlayerNameDto(
  Long memberId,
  String nickname,
  Integer roomScore,
  Integer score,
  Integer rank
) {

}
