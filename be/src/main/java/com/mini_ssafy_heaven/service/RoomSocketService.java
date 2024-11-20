package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.MessageResponse;

public interface RoomSocketService {

  MessageResponse<EnterResponse> enter(Long roomId, EnterRequest request);

}
