package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomGameDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import com.mini_ssafy_heaven.dto.request.ChatRequest;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ExitRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.response.ChatResponse;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.ExitResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;
import com.mini_ssafy_heaven.dto.response.StartResponse;
import com.mini_ssafy_heaven.global.annotation.Lock;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import com.mini_ssafy_heaven.global.exception.code.RoomPlayerErrorCode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomSocketServiceImpl implements RoomSocketService {

  private final RoomPlayerDao roomPlayerDao;
  private final RoomDao roomDao;
  private final RoomGameDao roomGameDao;

  @Override
  public EnterResponse enter(Long roomId, EnterRequest request) {
    List<RoomPlayerNameDto> players = roomPlayerDao.findAllWithNamesByRoomId(roomId);

    return EnterResponse.from(players, request.nickname());
  }

  @Override
  @Transactional
  @Lock("PLAYER-READY")
  public ReadyResponse toggleReady(Long roomId, ReadyRequest request) {
    List<RoomPlayer> roomPlayers = roomPlayerDao.findAllByRoomId(roomId);
    long readyCount = countReady(roomPlayers);

    RoomPlayer toggled = roomPlayers.stream()
        .filter(player -> Objects.equals(request.memberId(), player.getMemberId()))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException(RoomPlayerErrorCode.NOT_JOINED.getMessage()))
        .toggleReady();

    int updated = roomPlayerDao.update(toggled);
    long updatedReadyCount = toggled.isReady() ? readyCount + updated : readyCount - updated;

    return ReadyResponse.from(toggled, (int) updatedReadyCount, roomPlayers.size());
  }

  @Override
  @Transactional
  public ExitResponse exit(Long roomId, ExitRequest request) {
    RoomPlayer roomPlayer = roomPlayerDao.findByRoomAndMember(roomId, request.memberId())
        .orElseThrow(() -> new NoSuchElementException(RoomPlayerErrorCode.NOT_JOINED.getMessage()));

    roomPlayerDao.deleteById(roomPlayer.getId());

    List<RoomPlayer> players = roomPlayerDao.findAllByRoomId(roomId);

    if (players.isEmpty()) {
      deleteRoom(roomId);

      return ExitResponse.empty();
    }

    if (roomPlayer.isManager()) {
      RoomPlayer candidate = players.get(0);
      RoomPlayer newManager = candidate.promoteToManager();

      roomPlayerDao.update(newManager);
    }

    List<RoomPlayerNameDto> newPlayers = roomPlayerDao.findAllWithNamesByRoomId(roomId);

    return ExitResponse.from(request.nickname(), newPlayers);
  }

  @Override
  public ChatResponse chat(ChatRequest request) {
    return ChatResponse.from(request.nickname(), request.chat());
  }

  @Override
  @Transactional
  @Lock("PLAYER-READY")
  public StartResponse start(Long roomId) {
    List<RoomPlayer> players = roomPlayerDao.findAllByRoomId(roomId);
    long readyCount = countReady(players);

    if (players.size() != readyCount) {
      throw new IllegalStateException(RoomErrorCode.NOT_READY_YET.getMessage());
    }

    Room room = roomDao.findById(roomId)
        .orElseThrow(
            () -> new NoSuchElementException(RoomErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage()));
    Room started = room.start();

    roomDao.update(started);

    return new StartResponse(started.getStatus());
  }

  private void deleteRoom(Long roomId) {
    roomGameDao.deleteByRoomId(roomId);
    roomDao.deleteById(roomId);
  }

  private long countReady(List<RoomPlayer> roomPlayers) {
    return roomPlayers.stream()
        .filter(RoomPlayer::isReady)
        .count();
  }

}
