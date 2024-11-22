package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import java.util.List;
import lombok.Builder;

@Builder
public record RoomDetailResponse(Long id, String title, List<RoomPlayerNameDto> roomPlayers) {

  public static RoomDetailResponse from(Room room, List<RoomPlayerNameDto> roomPlayers) {
    return RoomDetailResponse.builder()
        .id(room.getId())
        .title(room.getTitle())
        .roomPlayers(roomPlayers)
        .build();
  }

}
