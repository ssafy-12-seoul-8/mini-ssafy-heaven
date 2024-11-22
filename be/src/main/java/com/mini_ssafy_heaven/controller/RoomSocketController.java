package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;
import com.mini_ssafy_heaven.global.annotation.StompController;
import com.mini_ssafy_heaven.service.RoomSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@StompController
@RequiredArgsConstructor
public class RoomSocketController {

  private final RoomSocketService roomSocketService;

  @MessageMapping("/{id}/enter")
  @SendTo("/game/{id}")
  public MessageResponse<EnterResponse> enterRoom(
    @DestinationVariable("id") Long id,
    EnterRequest request
  ) {
    EnterResponse response = roomSocketService.enter(id, request);

    return MessageResponse.enter(response);
  }

  @MessageMapping("/{id}/ready")
  @SendTo("/game/{id}")
  public MessageResponse<ReadyResponse> toggleReady(
    @DestinationVariable("id") Long id,
    ReadyRequest request
  ) {
    ReadyResponse response = roomSocketService.toggleReady(id, request);

    return MessageResponse.ready(response);
  }

}
