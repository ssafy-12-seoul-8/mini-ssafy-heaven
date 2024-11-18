package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.query.SimpleRoomDto;
import java.util.List;
import lombok.Builder;

@Builder
public record BasicRoomResponse(
    Long id,
    String title,
    String manager,
    Integer currentPlayerCount,
    Integer capacity,
    List<RoomGameTitleDto> roomGames
) {

  public static BasicRoomResponse from(
      SimpleRoomDto room,
      int playerCount,
      List<RoomGameTitleDto> roomGames
  ) {
    return BasicRoomResponse.builder()
        .id(room.id())
        .title(room.title())
        .manager(room.manager())
        .currentPlayerCount(playerCount)
        .capacity(room.capacity())
        .roomGames(roomGames)
        .build();
  }

}
