package com.mini_ssafy_heaven.dto.request;

import com.mini_ssafy_heaven.domain.enums.SocketMessageType;

public record MessageRequest(Long memberId, SocketMessageType type, String content) {

}
