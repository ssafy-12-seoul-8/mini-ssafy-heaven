package com.mini_ssafy_heaven.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mini_ssafy_heaven.domain.RoomPlayer.RoomPlayerBuilder;
import com.mini_ssafy_heaven.domain.enums.RoomPlayerRole;
import com.mini_ssafy_heaven.fixture.BaseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomPlayerTest {

  Long memberId;
  Long roomId;

  @BeforeEach
  void setUp() {
    memberId = BaseFixture.getRandomId();
    roomId = BaseFixture.getRandomId();
  }

  @Nested
  class 방_플레이어_생성_테스트 {

    @Test
    void 방_매니저를_생성한다() {
      // given

      // when
      RoomPlayer manager = RoomPlayer.createManager(memberId, roomId);

      // then
      assertThat(manager).hasFieldOrPropertyWithValue("memberId", memberId)
          .hasFieldOrPropertyWithValue("roomId", roomId)
          .hasFieldOrPropertyWithValue("role", RoomPlayerRole.MANAGER.getRole());
    }

    @Test
    void role이_없으면_플레이어가_생성된다() {
      // given
      RoomPlayerBuilder builder = RoomPlayer.builder()
          .memberId(memberId)
          .roomId(roomId);

      // when
      RoomPlayer roomPlayer = builder.build();

      // then
      assertThat(roomPlayer.getRole()).isEqualTo(RoomPlayerRole.PLAYER.getRole());
    }

  }

}
