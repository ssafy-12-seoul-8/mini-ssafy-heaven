package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.enums.SocketMessageType;

public record MessageResponse(SocketMessageType type, String message) {

}
