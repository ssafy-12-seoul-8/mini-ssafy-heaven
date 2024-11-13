package com.mini_ssafy_heaven.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import com.mini_ssafy_heaven.domain.Room.RoomBuilder;
import com.mini_ssafy_heaven.domain.enums.RoomStatus;
import com.mini_ssafy_heaven.fixture.BaseFixture;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;

@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomTest {

  String title;

  @BeforeEach
  void setUp() {
    title = BaseFixture.getRandomSentenceWithMax(20);
  }

  @Nested
  class 방_생성_테스트 {

    @Test
    void 방을_생성할_수_있다() {
      // given

      // when
      Room room = Room.builder()
          .title(title)
          .build();

      // then
      assertThat(room).hasFieldOrPropertyWithValue("title", title)
          .hasFieldOrPropertyWithValue("status", RoomStatus.CREATING);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 타이틀이_없으면_방_생성을_실패한다(String invalidTitle) {
      // given
      RoomBuilder builder = Room.builder()
          .title(invalidTitle);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.EMPTY_TITLE.getMessage());
    }

    @Test
    void 타이틀이_20자를_넘어가면_방_생성을_실패한다() {
      // given
      String invalidTitle = BaseFixture.getRandomSentence(21, 100);
      RoomBuilder builder = Room.builder()
          .title(invalidTitle);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomErrorCode.EXCESSIVE_TITLE_LENGTH.getMessage());
    }

  }

}
