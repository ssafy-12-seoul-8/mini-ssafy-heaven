package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.response.GameResponse;

public interface GamePlayService<T> {

  GameResponse<T> start(Long roomId);

}