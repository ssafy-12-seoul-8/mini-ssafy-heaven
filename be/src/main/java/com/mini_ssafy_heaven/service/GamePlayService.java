package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;

public interface GamePlayService<T> {

  GameResponse<T> start(Long roomId);

  GameResponse<DescriptionReadResponse> readDescription(Long roomId);

  default GameResponse<T> setAnswer(Long roomId, String answer) {
    throw new UnsupportedOperationException("Not supported.");
  }

}
