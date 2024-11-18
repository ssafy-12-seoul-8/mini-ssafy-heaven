package com.mini_ssafy_heaven.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayNameGeneration(ReplaceUnderscores.class)
class GameServiceImplTest {

  @Autowired
  GameService gameService;

  @Nested
  class 게임_전체_조회_서비스_테스트 {

    @Test
    void 게임_전체_조회를_성공한다() {
      // given
      // TODO: 게임 생성 DAO 구현 이후 수정

      // when
      List<GameDetailResponse> actual = gameService.getAll();

      // then
      assertThat(actual).isNotEmpty();
    }

  }

}