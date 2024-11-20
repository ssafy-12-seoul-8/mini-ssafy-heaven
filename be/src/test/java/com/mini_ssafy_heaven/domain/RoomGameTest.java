package com.mini_ssafy_heaven.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.mini_ssafy_heaven.domain.RoomGame.RoomGameBuilder;
import com.mini_ssafy_heaven.fixture.BaseFixture;
import com.mini_ssafy_heaven.global.exception.code.RoomGameErrorCode;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomGameTest {

  Integer currentRound;
  Integer roundLimit;

  @BeforeEach
  void setUp() {
    // TODO: 게임 구현 후 대체
    roundLimit = BaseFixture.getRandomInt(3, 10);
    currentRound = BaseFixture.getRandomInt(1, roundLimit);
  }

  @Nested
  class 방_게임_생성 {

    @Test
    void 방_게임_생성을_성공한다() {
      // given
      RoomGameBuilder builder = RoomGame.builder()
          .currentRound(currentRound)
          .roundLimit(roundLimit);

      // when
      RoomGame roomGame = builder.build();

      // then
      assertThat(roomGame).hasFieldOrPropertyWithValue("currentRound", currentRound)
          .hasFieldOrPropertyWithValue("roundLimit", roundLimit);
    }

    @ParameterizedTest
    @NullSource
    void 현재_라운드가_null이면_1로_생성한다(Integer nullRound) {
      // given
      RoomGameBuilder builder = RoomGame.builder()
          .currentRound(nullRound)
          .roundLimit(roundLimit);

      // then
      RoomGame roomGame = builder.build();

      // when
      assertThat(roomGame.getCurrentRound()).isEqualTo(1);
    }

    @ParameterizedTest
    @NullSource
    void 라운드_제한이_null이면_생성을_실패한다(Integer nullRoundLimit) {
      // given
      RoomGameBuilder builder = RoomGame.builder()
          .currentRound(currentRound)
          .roundLimit(nullRoundLimit);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomGameErrorCode.NULL_ROUND_LIMIT.getMessage());
    }

    @Test
    void 라운드_제한이_양수가_아니면_생성을_실패한다() {
      // given
      Integer invalidRoundLimit = BaseFixture.getRandomNegative();
      RoomGameBuilder builder = RoomGame.builder()
          .roundLimit(invalidRoundLimit)
          .currentRound(currentRound);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomGameErrorCode.POSITIVE_ROUND_LIMIT_REQUIRED.getMessage());
    }

    @Test
    void 현재_라운드가_양수가_아니면_생성을_실패한다() {
      // given
      Integer negativeCurrentRound = BaseFixture.getRandomNegative();
      RoomGameBuilder builder = RoomGame.builder()
          .currentRound(negativeCurrentRound)
          .roundLimit(roundLimit);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomGameErrorCode.POSITIVE_CURRENT_ROUND_REQUIRED.getMessage());
    }

    @Test
    void 현재_라운드가_라운드_제한보다_크면_생성을_실패한다() {
      // given
      Integer excessiveCurrentRound = BaseFixture.getRandomInt(roundLimit + 1, roundLimit + 10);
      RoomGameBuilder builder = RoomGame.builder()
          .currentRound(excessiveCurrentRound)
          .roundLimit(roundLimit);

      // when
      ThrowingCallable create = builder::build;

      // then
      assertThatIllegalArgumentException().isThrownBy(create)
          .withMessage(RoomGameErrorCode.EXCESSIVE_CURRENT_ROUND.getMessage());
    }

  }

}