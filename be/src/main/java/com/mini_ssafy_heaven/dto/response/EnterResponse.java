package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import io.netty.util.internal.logging.MessageFormatter;
import java.util.List;

public record EnterResponse(List<RoomPlayerNameDto> players, String message) {

  private final static String template = "{} 님이 입장하셨습니다.";

  public static EnterResponse from(List<RoomPlayerNameDto> players, String currentMemberNickname) {
    String message = MessageFormatter.format(template, currentMemberNickname)
        .getMessage();

    return new EnterResponse(players, message);
  }

}
