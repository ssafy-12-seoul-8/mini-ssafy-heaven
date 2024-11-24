package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import java.util.Collections;
import java.util.List;
import org.slf4j.helpers.MessageFormatter;

public record ExitResponse(String message, List<RoomPlayerNameDto> players) {

  private static final String TEMPLATE = "{}님이 퇴장하셨습니다.";

  public static ExitResponse from(String nickname, List<RoomPlayerNameDto> players) {
    String message = MessageFormatter.format(TEMPLATE, nickname)
        .toString();

    return new ExitResponse(message, players);
  }

  public static ExitResponse empty() {
    return new ExitResponse(null, Collections.emptyList());
  }

}
