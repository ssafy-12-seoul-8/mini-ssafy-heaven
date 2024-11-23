package com.mini_ssafy_heaven.dto.query;

import com.mini_ssafy_heaven.domain.enums.RoomStatus;

public record SimpleRoomDto(
  Long id,
  String title,
  Integer capacity,
  String manager,
  RoomStatus status
) {

}
