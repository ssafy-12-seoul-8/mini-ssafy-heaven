package com.mini_ssafy_heaven.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomGameDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.request.CreateRoomGameDto;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.ScrollRequest;
import com.mini_ssafy_heaven.dto.response.BasicRoomResponse;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.dto.response.ScrollResponse;
import com.mini_ssafy_heaven.fixture.BaseFixture;
import com.mini_ssafy_heaven.fixture.RoomFixture;
import com.mini_ssafy_heaven.fixture.RoomGameFixture;
import com.mini_ssafy_heaven.fixture.RoomPlayerFixture;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomServiceTest {

  @Autowired
  RoomService roomService;

  @Autowired
  RoomPlayerDao roomPlayerDao;

  @Autowired
  RoomDao roomDao;

  @Autowired
  RoomGameDao roomGameDao;

  String title;
  Integer capacity;
  List<CreateRoomGameDto> roomGames;
  Long loginId;

  @BeforeEach
  void setUp() {
    title = BaseFixture.getRandomSentenceWithMax(20);
    capacity = BaseFixture.getRandomInt(1, 5);

    // TODO: 전체 조회 쿼리 구현 이후 수정
    roomGames = List.of(new CreateRoomGameDto(1L, 3));

    // TODO: 로그인 구현 이후 수정
    loginId = 1L;
  }

  @Nested
  class 방_생성_서비스_테스트 {

    @Test
    void 방_생성을_성공한다() {
      // given
      CreateRoomRequest request = new CreateRoomRequest(title, capacity, roomGames);

      // when
      CreateRoomResponse response = roomService.create(request, loginId);

      // then
      // TODO: 방 조회 로직 구현 후 수정
      assertThat(response.id()).isEqualTo(2L);
    }

    @Test
    void 로그인_사용자가_없는_사용자면_방_생성을_실패한다() {
      // given
      Long invalidId = BaseFixture.getRandomLong(2, 100);
      CreateRoomRequest request = new CreateRoomRequest(title, capacity, roomGames);

      // when
      ThrowingCallable create = () -> roomService.create(request, invalidId);

      // then
      assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(create)
          .withMessage(RoomErrorCode.LOGIN_USER_NOT_EXISTS.getMessage());
    }

    @Test
    void 이미_다른_방에_참여하고_있으면_방_생성을_실패한다() {
      // given
      Room room = Room.builder()
          .title(title)
          .build();

      roomDao.save(room);

      RoomPlayer manager = RoomPlayer.createManager(loginId, room.getId());

      roomPlayerDao.save(manager);

      CreateRoomRequest request = new CreateRoomRequest(title, capacity, roomGames);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatExceptionOfType(DuplicateKeyException.class).isThrownBy(create)
          .withMessage(RoomErrorCode.JOINED_PLAYER.getMessage());
    }

    @Test
    void 게임이_포함되어_있지_않으면_방_생성을_실패한다() {
      // given
      List<CreateRoomGameDto> emptyRoomGames = Collections.emptyList();
      CreateRoomRequest request = new CreateRoomRequest(title, capacity, emptyRoomGames);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.EMPTY_GAMES.getMessage());
    }

    @Test
    void 존재하지_않는_게임이_포함되어_있으면_방_생성을_실패한다() {
      // given
      List<CreateRoomGameDto> invalidRoomGames = List.of(new CreateRoomGameDto(2L, 3));
      CreateRoomRequest request = new CreateRoomRequest(title, capacity, invalidRoomGames);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.GAME_NOT_AVAILABLE.getMessage());
    }

  }

  @Nested
  class 방_전체_조회_서비스_테스트 {

    Room room;

    @BeforeEach
    void setUp() {
      room = RoomFixture.createAllRandom();

      roomDao.save(room);

      RoomPlayer manager = RoomPlayerFixture.createManager(room.getId(), loginId);

      roomPlayerDao.save(manager);

      // TODO: 게임 구현 후 수정
      List.of(RoomGameFixture.createWithRoomAndGame(room.getId(), 1L))
          .forEach(roomGameDao::save);
    }

    // TODO: 회원 생성 구현 이후 디벨롭
    @Test
    void 방_전체_조회를_성공한다() {
      // given
      ScrollRequest request = new ScrollRequest(null, null);

      // when
      ScrollResponse<BasicRoomResponse> response = roomService.getAll(request);

      // then
      assertThat(response.nextCursor()).isEqualTo(room.getId());
    }

  }

}
