package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.enums.SocketMessageType;

public record MessageResponse<T>(SocketMessageType type, T payload) {

  public static <T> MessageResponse<T> enter(T payload) {
    return new MessageResponse<>(SocketMessageType.ENTER, payload);
  }

  public static <T> MessageResponse<T> ready(T payload) {
    return new MessageResponse<>(SocketMessageType.READY, payload);
  }

  public static <T> MessageResponse<T> exit(T payload) {
    return new MessageResponse<>(SocketMessageType.EXIT, payload);
  }

  public static <T> MessageResponse<T> chat(T payload) {
    return new MessageResponse<>(SocketMessageType.TALK, payload);
  }

  public static <T> MessageResponse<T> start(T payload) {
    return new MessageResponse<>(SocketMessageType.START, payload);
  }

  public static <T> MessageResponse<T> score(T payload) {
    return new MessageResponse<>(SocketMessageType.SCORE, payload);
  }

  public static <T> MessageResponse<T> game(T payload) {
    return new MessageResponse<>(SocketMessageType.GAME, payload);
  }

}
