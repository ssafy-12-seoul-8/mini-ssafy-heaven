package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.dto.response.RoundStartResponse;

public interface GamePlayService<T> {

  GameResponse<T> start(Long roomId);

  GameResponse<DescriptionReadResponse> readDescription(Long roomId);

  GameResponse<RoundStartResponse> roundStart(Long roomId);

  default GameResponse<T> setAnswer(Long roomId, String answer) {
    throw new UnsupportedOperationException("Not supported.");
  }

}
