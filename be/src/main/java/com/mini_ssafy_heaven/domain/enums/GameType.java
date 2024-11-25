package com.mini_ssafy_heaven.domain.enums;

import com.mini_ssafy_heaven.global.exception.code.GameTypeErrorCode;
import com.mini_ssafy_heaven.service.BaseballPlayService;
import com.mini_ssafy_heaven.service.GamePlayService;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
public enum GameType {
  BASEBALL("숫자야구");

  private final String name;

  private GamePlayService<?> gamePlayService;

  public static GameType get(String name) {
    return Arrays.stream(GameType.values())
        .filter(type -> Objects.equals(name, type.name))
        .findFirst()
        .orElseThrow(
            () -> new NoSuchElementException(GameTypeErrorCode.NO_SUCH_GAME_TYPE.getMessage()));
  }

  private void setService(GamePlayService<?> gamePlayService) {
    this.gamePlayService = gamePlayService;
  }

  @Component
  @RequiredArgsConstructor
  private static class GamePlayServiceInjector {

    private final BaseballPlayService baseballPlayService;

    @PostConstruct
    public void postConstruct() {
      GameType.BASEBALL.setService(baseballPlayService);
    }

  }
}
