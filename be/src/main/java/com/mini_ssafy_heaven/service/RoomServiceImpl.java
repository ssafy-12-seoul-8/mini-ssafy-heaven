package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.GameDao;
import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomGameDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Member;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomGame;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import com.mini_ssafy_heaven.dto.query.SimpleRoomDto;
import com.mini_ssafy_heaven.dto.request.CreateRoomGameDto;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.ScrollRequest;
import com.mini_ssafy_heaven.dto.request.UpdateRoomStatusRequest;
import com.mini_ssafy_heaven.dto.response.BasicRoomResponse;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.dto.response.RoomDetailResponse;
import com.mini_ssafy_heaven.dto.response.RoomGameTitleDto;
import com.mini_ssafy_heaven.dto.response.ScrollResponse;
import com.mini_ssafy_heaven.global.annotation.Lock;
import com.mini_ssafy_heaven.global.exception.code.MemberErrorCode;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import com.mini_ssafy_heaven.global.exception.code.RoomPlayerErrorCode;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(readOnly = true)
  public ScrollResponse<BasicRoomResponse> getAll(ScrollRequest request) {
    List<SimpleRoomDto> rooms = roomDao.selectAll(request.cursor(), request.size() + 1);
    List<BasicRoomResponse> list = rooms.stream()
        .limit(request.size())
        .map(this::buildBasicRoom)
        .toList();
    boolean hasNext = Objects.equals(request.size() + 1, rooms.size());
    Long nextCursor = getNextCursor(hasNext, rooms);

    return ScrollResponse.from(hasNext, list, nextCursor);
  }

  @Override
  @Transactional
  @Lock("PLAYER-JOIN")
  public void join(Long id, Long loginId) {
    Room room = roomDao.findById(id)
        .orElseThrow(
            () -> new NoSuchElementException(RoomErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage()));

    if (!room.isPossibleToEnter()) {
      throw new IllegalStateException(RoomErrorCode.NOT_POSSIBLE_TO_ENTER.getMessage());
    }

    List<RoomPlayer> players = roomPlayerDao.findAllByRoomId(id);

    if (isInRoom(players, loginId)) {
      return;
    }

    if (players.size() >= room.getCapacity()) {
      throw new IllegalStateException(RoomErrorCode.FULL_ROOM.getMessage());
    }

    validatePlayerNotJoined(loginId);

    RoomPlayer roomPlayer = RoomPlayer.createPlayer(loginId, id);

    roomPlayerDao.save(roomPlayer);
  }

  @Override
  @Transactional
  public void updateStatus(Long id, UpdateRoomStatusRequest request, Long loginId) {
    Room room = roomDao.findById(id)
        .orElseThrow(
          () -> new NoSuchElementException(RoomErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage())
        );

    validateManager(loginId, room.getId());

    Room updated = room.updateStatus(request.status());

    roomDao.update(updated);
  }

  @Override
  @Transactional(readOnly = true)
  public RoomDetailResponse getDetail(Long id, Long loginId) {
    Member member = memberDao.findById(loginId)
        .orElseThrow(
          () -> new NoSuchElementException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
        );
    Room room = roomDao.findById(id)
        .orElseThrow(
          () -> new NoSuchElementException(RoomErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage())
        );
    List<RoomPlayerNameDto> players = roomPlayerDao.findAllWithNamesByRoomId(room.getId());

    validatePlayerInRoom(players, member.getId());

    List<RoomGameTitleDto> roomGames = roomGameDao.findTitlesByRoomId(room.getId());
    long readyCount = countReady(players);

    return RoomDetailResponse.from(room, players, roomGames, readyCount);
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

  private BasicRoomResponse buildBasicRoom(SimpleRoomDto room) {
    int playerCount = roomPlayerDao.countPlayersByRoomId(room.id());
    List<RoomGameTitleDto> roomGames = roomGameDao.findTitlesByRoomId(room.id());

    return BasicRoomResponse.from(room, playerCount, roomGames);
  }

  private Long getNextCursor(boolean hasNext, List<SimpleRoomDto> rooms) {
    if (rooms.isEmpty()) {
      return 0L;
    }

    int last = hasNext ? rooms.size() - 2 : rooms.size() - 1;

    return rooms.get(last)
        .id();
  }

  private void validateManager(Long memberId, Long roomId) {
    RoomPlayer roomPlayer = roomPlayerDao.findByRoomAndMember(roomId, memberId)
        .orElseThrow(() -> new NoSuchElementException(RoomPlayerErrorCode.NOT_JOINED.getMessage()));

    if (!roomPlayer.isManager()) {
      throw new IllegalArgumentException(RoomPlayerErrorCode.NOT_A_MANAGER.getMessage());
    }
  }

  private boolean isInRoom(List<RoomPlayer> players, Long memberId) {
    return players.stream()
        .anyMatch((player) -> player.isSameUser(memberId));
  }

  private void validatePlayerInRoom(List<RoomPlayerNameDto> players, Long memberId) {
    boolean notFound = players.stream()
        .noneMatch(player -> Objects.equals(player.memberId(), memberId));

    if (notFound) {
      throw new IllegalStateException(RoomPlayerErrorCode.NOT_JOINED.getMessage());
    }
  }

  private long countReady(List<RoomPlayerNameDto> roomPlayers) {
    return roomPlayers.stream()
        .filter(player -> player.status().isReady())
        .count();
  }

}
