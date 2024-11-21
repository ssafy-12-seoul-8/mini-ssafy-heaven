package com.mini_ssafy_heaven.domain.enums;

import io.netty.util.internal.logging.MessageFormatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SocketMessageType {
  ENTER("{}님이 입장하셨습니다."),
  EXIT("{}님이 나가셨습니다."),
  TALK(""),
  READY(""),
  SCORE(""),
  GAME_OVER(""),
  ALL_OVER("");

  private final String messageTemplate;

  public String format(String value) {
    return MessageFormatter.format(this.messageTemplate, value)
        .toString();
  }
}
