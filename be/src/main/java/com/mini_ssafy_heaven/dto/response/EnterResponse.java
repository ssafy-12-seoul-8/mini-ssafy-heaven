package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import java.util.List;
import org.slf4j.helpers.MessageFormatter;

public record EnterResponse(List<RoomPlayerNameDto> players, String message) {

  private final static String template = "{} 님이 입장하셨습니다.";

  public static EnterResponse from(List<RoomPlayerNameDto> players, String currentMemberNickname) {
    String message = MessageFormatter.format(template, currentMemberNickname)
        .getMessage();

    return new EnterResponse(players, message);
  }

}
