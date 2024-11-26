package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.dto.request.ChatRequest;
import com.mini_ssafy_heaven.dto.request.DescriptionReadRequest;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ExitRequest;
import com.mini_ssafy_heaven.dto.request.GameRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.request.RoundStartRequest;
import com.mini_ssafy_heaven.dto.request.SetAnswerRequest;
import com.mini_ssafy_heaven.dto.response.ChatResponse;
import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.ExitResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;
import com.mini_ssafy_heaven.dto.response.StartResponse;
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
      @DestinationVariable("id")
      Long id,
      EnterRequest request
  ) {
    EnterResponse response = roomSocketService.enter(id, request);

    return MessageResponse.enter(response);
  }

  @MessageMapping("/{id}/ready")
  @SendTo("/game/{id}")
  public MessageResponse<ReadyResponse> toggleReady(
      @DestinationVariable("id")
      Long id,
      ReadyRequest request
  ) {
    ReadyResponse response = roomSocketService.toggleReady(id, request);

    return MessageResponse.ready(response);
  }

  @MessageMapping("/{id}/exit")
  @SendTo("/game/{id}")
  public MessageResponse<ExitResponse> exitRoom(
      @DestinationVariable("id")
      Long id,
      ExitRequest request
  ) {
    ExitResponse response = roomSocketService.exit(id, request);

    return MessageResponse.exit(response);
  }

  @MessageMapping("/{id}/talk")
  @SendTo("/game/{id}")
  public MessageResponse<ChatResponse> chat(
      @DestinationVariable("id")
      Long id,
      ChatRequest request
  ) {
    ChatResponse response = roomSocketService.chat(request);

    return MessageResponse.chat(response);
  }

  @MessageMapping("/{id}/start")
  @SendTo("/game/{id}")
  public MessageResponse<StartResponse> start(
      @DestinationVariable("id")
      Long id
  ) {
    StartResponse response = roomSocketService.start(id);

    return MessageResponse.start(response);
  }

  @MessageMapping("/{id}/game/start")
  @SendTo("/game/{id}")
  public MessageResponse<GameResponse<?>> gameStart(
      @DestinationVariable("id")
      Long id, GameRequest request
  ) {
    GameResponse<?> response = roomSocketService.gameStart(id, request);

    return MessageResponse.game(response);
  }

  @MessageMapping("/{id}/game/set-answer")
  @SendTo("/game/{id}")
  public MessageResponse<GameResponse<?>> gameSetAnswer(@DestinationVariable("id") Long id, SetAnswerRequest request) {
    GameResponse<?> response = roomSocketService.gameSetAnswer(id, request);

    return MessageResponse.game(response);
  }

  @MessageMapping("/{id}/game/confirm")
  @SendTo("/game/{id}")
  public MessageResponse<GameResponse<DescriptionReadResponse>> countRead(@DestinationVariable("id") Long id, DescriptionReadRequest request) {
    GameResponse<DescriptionReadResponse> response = roomSocketService.countRead(
        id, request);

    return MessageResponse.game(response);
  }

  @MessageMapping("/{id}/game/round-start")
  @SendTo("/game/{id}")
  public MessageResponse<GameResponse<Void>> roundStart(@DestinationVariable("id") Long id, RoundStartRequest request) {
    GameResponse<Void> response = roomSocketService.roundStart(request);

    return MessageResponse.game(response);
  }

}
