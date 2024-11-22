package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.request.ExitRequest;
import com.mini_ssafy_heaven.dto.request.ReadyRequest;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.ExitResponse;
import com.mini_ssafy_heaven.dto.response.ReadyResponse;
import com.mini_ssafy_heaven.global.annotation.Lock;
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
  public ExitResponse exit(Long roomId, ExitRequest request) {
    // TODO Auto-generated method stub
    return null;
  }

  private long countReady(List<RoomPlayer> roomPlayers) {
    return roomPlayers.stream()
        .filter(RoomPlayer::isReady)
        .count();
  }

}
