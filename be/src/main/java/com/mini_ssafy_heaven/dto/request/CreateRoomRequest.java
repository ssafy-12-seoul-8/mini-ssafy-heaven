package com.mini_ssafy_heaven.dto.request;

import java.util.List;

public record CreateRoomRequest(
    String title,
    Integer capacity,
    List<CreateRoomGameDto> roomGames
) {

}

