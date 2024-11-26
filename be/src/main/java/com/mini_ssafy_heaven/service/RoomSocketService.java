package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.ChatRequest;
import com.mini_ssafy_heaven.dto.request.DescriptionReadRequest;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ExitRequest;
import com.mini_ssafy_heaven.dto.request.GameRequest;
import com.mini_ssafy_heaven.dto.request.GameTryRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.request.RoundStartRequest;
import com.mini_ssafy_heaven.dto.request.ScoreRequest;
import com.mini_ssafy_heaven.dto.request.SetAnswerRequest;
import com.mini_ssafy_heaven.dto.response.ChatResponse;
import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.ExitResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.dto.response.GameTryResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;
import com.mini_ssafy_heaven.dto.response.ScoreResponse;
import com.mini_ssafy_heaven.dto.response.StartResponse;

public interface RoomSocketService {

  EnterResponse enter(Long roomId, EnterRequest request);

  ReadyResponse toggleReady(Long roomId, ReadyRequest request);

  ExitResponse exit(Long roomId, ExitRequest request);

  ChatResponse chat(ChatRequest request);

  StartResponse start(Long roomId);

  ScoreResponse score(Long roomId, ScoreRequest request);

  GameResponse<?> gameStart(Long roomId, GameRequest request);

  GameResponse<?> gameSetAnswer(Long roomId, SetAnswerRequest request);

  GameResponse<DescriptionReadResponse> countRead(Long roomId, DescriptionReadRequest request);

  GameResponse<Void> roundStart(RoundStartRequest request);

  GameResponse<GameTryResponse> gameTry(Long roomId, GameTryRequest request);

}
