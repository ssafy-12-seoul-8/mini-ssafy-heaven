package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.enums.SocketMessageType;

public record MessageResponse<T>(SocketMessageType type, T payload) {

  public static <T> MessageResponse<T> from(SocketMessageType type, T payload) {
    return new MessageResponse<T>(type, payload);
  }

}
