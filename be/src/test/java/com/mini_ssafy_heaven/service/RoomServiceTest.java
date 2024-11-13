package com.mini_ssafy_heaven.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
import com.mini_ssafy_heaven.dao.RoomDao;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.fixture.BaseFixture;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;

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

  String title;
  List<Long> gameIds;
  Long loginId;

  @BeforeEach
  void setUp() {
    title = BaseFixture.getRandomSentenceWithMax(20);

    // TODO: 전체 조회 쿼리 구현 이후 수정
    gameIds = List.of(1L);

    // TODO: 로그인 구현 이후 수정
    loginId = 1L;
  }

  @Nested
  class 방_생성_서비스_테스트 {

    @Test
    void 방_생성을_성공한다() {
      // given
      CreateRoomRequest request = new CreateRoomRequest(title, gameIds);

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
      CreateRoomRequest request = new CreateRoomRequest(title, gameIds);

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

      CreateRoomRequest request = new CreateRoomRequest(title, gameIds);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatExceptionOfType(DuplicateKeyException.class).isThrownBy(create)
          .withMessage(RoomErrorCode.JOINED_PLAYER.getMessage());
    }

    @Test
    void 게임이_포함되어_있지_않으면_방_생성을_실패한다() {
      // given
      List<Long> emptyGameIds = Collections.emptyList();
      CreateRoomRequest request = new CreateRoomRequest(title, emptyGameIds);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.EMPTY_GAMES.getMessage());
    }

    @Test
    void 존재하지_않는_게임이_포함되어_있으면_방_생성을_실패한다() {
      // given
      List<Long> invalidGameIds = List.of(2L);
      CreateRoomRequest request = new CreateRoomRequest(title, invalidGameIds);

      // when
      ThrowingCallable create = () -> roomService.create(request, loginId);

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.GAME_NOT_AVAILABLE.getMessage());
    }

  }

}
