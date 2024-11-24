package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.enums.SocketMessageType;

public record MessageResponse<T>(SocketMessageType type, T payload) {

  public static <T> MessageResponse<T> enter(T payload) {
    return new MessageResponse<T>(SocketMessageType.ENTER, payload);
  }

  public static <T> MessageResponse<T> ready(T payload) {
    return new MessageResponse<T>(SocketMessageType.READY, payload);
  }

  public static <T> MessageResponse<T> exit(T payload) {
    return new MessageResponse<T>(SocketMessageType.EXIT, payload);
  }

  public static <T> MessageResponse<T> chat(T payload) {
    return new MessageResponse<T>(SocketMessageType.TALK, payload);
  }

  public static <T> MessageResponse<T> start(T payload) {
    return new MessageResponse<T>(SocketMessageType.START, payload);
  }

}
