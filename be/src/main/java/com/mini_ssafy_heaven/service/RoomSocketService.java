package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;

public interface RoomSocketService {

  MessageResponse<EnterResponse> enter(Long roomId, EnterRequest request);

  MessageResponse<ReadyResponse> toggleReady(Long roomId, ReadyRequest request);

}
