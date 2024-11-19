package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.dto.request.MessageRequest;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import com.mini_ssafy_heaven.service.RoomSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoomSocketController {

  private final RoomSocketService roomSocketService;

  @MessageMapping("/rooms/{id}")
  @SendTo("/game/{id}")
  public MessageResponse handleMessage(@DestinationVariable("id") Long id, MessageRequest request) {
    return roomSocketService.handleMessage(id, request);
  }

}
