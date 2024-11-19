package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.MessageRequest;
import com.mini_ssafy_heaven.dto.response.MessageResponse;

public interface RoomSocketService {

  MessageResponse handleMessage(Long roomId, MessageRequest request);

}
