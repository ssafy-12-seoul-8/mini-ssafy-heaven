package com.mini_ssafy_heaven.service;

import java.util.List;
import java.util.NoSuchElementException;
import com.mini_ssafy_heaven.dao.GameDao;
import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomGameDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomGame;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomDao roomDao;
  private final MemberDao memberDao;
  private final RoomPlayerDao roomPlayerDao;
  private final RoomGameDao roomGameDao;
  private final GameDao gameDao;

  @Override
  @Transactional
  public CreateRoomResponse create(CreateRoomRequest request, Long loginId) {
    validatePlayerNotJoined(loginId);
    validateGames(request.gameIds());

    Room room = Room.builder()
        .title(request.title())
        .build();

    roomDao.save(room);

    RoomPlayer manager = RoomPlayer.createManager(loginId, room.getId());

    roomPlayerDao.save(manager);
    request.gameIds()
        .stream()
        .map(gameId -> buildRoomGame(room.getId(), gameId))
        .forEach(roomGameDao::save);

    return new CreateRoomResponse(room.getId());
  }

  private void validatePlayerNotJoined(Long loginId) {
    if (!memberDao.existsById(loginId)) {
      throw new NoSuchElementException(RoomErrorCode.LOGIN_USER_NOT_EXISTS.getMessage());
    }

    if (roomPlayerDao.existsByMemberId(loginId)) {
      throw new DuplicateKeyException(RoomErrorCode.JOINED_PLAYER.getMessage());
    }
  }

  private void validateGames(List<Long> gameIds) {
    if (gameIds.isEmpty()) {
      throw new IllegalArgumentException(RoomErrorCode.EMPTY_GAMES.getMessage());
    }

    List<Long> matchingIds = gameDao.findIdsIn(gameIds);

    if (!matchingIds.containsAll(gameIds)) {
      throw new IllegalArgumentException(RoomErrorCode.GAME_NOT_AVAILABLE.getMessage());
    }
  }

  private RoomGame buildRoomGame(Long roomId, Long gameId) {
    return RoomGame.builder()
        .roomId(roomId)
        .gameId(gameId)
        .build();
  }

}
