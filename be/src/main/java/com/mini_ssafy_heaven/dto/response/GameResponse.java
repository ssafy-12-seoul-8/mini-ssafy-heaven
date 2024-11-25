package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.enums.GameMessageType;
import com.mini_ssafy_heaven.domain.enums.GameType;

public record GameResponse<T>(GameType gameType, GameMessageType gameMessageType, T payload) {

}
