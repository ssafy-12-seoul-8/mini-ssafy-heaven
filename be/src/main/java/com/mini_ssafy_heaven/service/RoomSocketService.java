package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.ChatRequest;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ExitRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.response.ChatResponse;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.ExitResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;

public interface RoomSocketService {

  EnterResponse enter(Long roomId, EnterRequest request);

  ReadyResponse toggleReady(Long roomId, ReadyRequest request);

  ExitResponse exit(Long roomId, ExitRequest request);

  ChatResponse chat(ChatRequest request);

}
