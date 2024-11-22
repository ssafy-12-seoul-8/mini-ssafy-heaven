package com.mini_ssafy_heaven.dto.response;

public record ChatResponse(String message) {

  public static ChatResponse from(String nickname, String chat) {
    String message = nickname + " : " + chat;

    return new ChatResponse(message);
  }

}
