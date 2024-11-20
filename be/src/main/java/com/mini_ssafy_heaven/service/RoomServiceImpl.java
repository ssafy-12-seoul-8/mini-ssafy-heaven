package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.GameDao;
import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomGameDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomGame;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.request.CreateRoomGameDto;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.global.annotation.Lock;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private static final int MAX_PLAYERS = 5;

  private final RoomDao roomDao;
  private final MemberDao memberDao;
  private final RoomPlayerDao roomPlayerDao;
  private final RoomGameDao roomGameDao;
  private final GameDao gameDao;

  @Override
  @Transactional
  public CreateRoomResponse create(CreateRoomRequest request, Long loginId) {
    validatePlayerNotJoined(loginId);
    validateGames(request.roomGames());

    Room room = Room.builder()
        .title(request.title())
        .capacity(request.capacity())
        .build();

    roomDao.save(room);

    RoomPlayer manager = RoomPlayer.createManager(loginId, room.getId());

    roomPlayerDao.save(manager);
    request.roomGames()
        .stream()
        .map(game -> buildRoomGame(room.getId(), game))
        .forEach(roomGameDao::save);

    return new CreateRoomResponse(room.getId());
  }

  @Override
  @Transactional
  @Lock("PLAYER-JOIN")
  public void join(Long roomId, Long loginId) {
    validateRoom(roomId);

    int count = roomPlayerDao.countByRoomId(roomId);

    if (count >= MAX_PLAYERS) {
      throw new IllegalStateException(RoomErrorCode.FULL_ROOM.getMessage());
    }

    validatePlayerNotJoined(loginId);

    RoomPlayer roomPlayer = RoomPlayer.createPlayer(loginId, roomId);

    roomPlayerDao.save(roomPlayer);
  }

  private void validatePlayerNotJoined(Long loginId) {
    if (!memberDao.existsById(loginId)) {
      throw new NoSuchElementException(RoomErrorCode.LOGIN_USER_NOT_EXISTS.getMessage());
    }

    if (roomPlayerDao.existsByMemberId(loginId)) {
      throw new DuplicateKeyException(RoomErrorCode.JOINED_PLAYER.getMessage());
    }
  }

  private void validateGames(List<CreateRoomGameDto> roomGames) {
    if (roomGames.isEmpty()) {
      throw new IllegalArgumentException(RoomErrorCode.EMPTY_GAMES.getMessage());
    }

    List<Long> gameIds = roomGames.stream()
        .map(CreateRoomGameDto::id)
        .toList();
    Set<Long> matchingIds = gameDao.findIdsIn(gameIds);

    if (!matchingIds.containsAll(gameIds)) {
      throw new IllegalArgumentException(RoomErrorCode.GAME_NOT_AVAILABLE.getMessage());
    }
  }

  private RoomGame buildRoomGame(Long roomId, CreateRoomGameDto game) {
    return RoomGame.builder()
        .roomId(roomId)
        .gameId(game.id())
        .roundLimit(game.roundLimit())
        .build();
  }

  private void validateRoom(Long roomId) {
    if (!roomDao.existsById(roomId)) {
      throw new NoSuchElementException(RoomErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage());
    }
  }

}
