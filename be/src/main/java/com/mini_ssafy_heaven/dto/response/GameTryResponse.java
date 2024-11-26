package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.dto.request.GameTryRequest;
import lombok.Builder;

@Builder
public record GameTryResponse(boolean isAnswer, boolean isOver, int nextCount, Long memberId, String message) {

  public static GameTryResponse from(boolean isAnswer, boolean isOver, int nextCount, Long memberId, String message) {
    return GameTryResponse.builder()
        .isAnswer(isAnswer)
        .isOver(isOver)
        .nextCount(nextCount)
        .memberId(memberId)
        .message(message)
        .build();
  }

}
