package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.dto.response.GameTryResponse;

public interface GamePlayService<T> {

  GameResponse<T> start(Long roomId);

  GameResponse<DescriptionReadResponse> readDescription(Long roomId, int totalCount);

  GameResponse<Void> roundStart();

  GameResponse<GameTryResponse> attempt(Long roomId, int currentCount, Long memberId, String trial);

  default GameResponse<T> setAnswer(Long roomId, String answer) {
    throw new UnsupportedOperationException("Not supported.");
  }

}
