package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import java.util.List;

public record AllOverResponse(List<RoomPlayerNameDto> roomPlayers) {

}
