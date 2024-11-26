package com.mini_ssafy_heaven.domain;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("BASEBALL")
@Builder
@Getter
public class BaseballCondition {

  @Id
  private final Long roomId;
  private final String currentBall;
  private final int ballCount;
  private final int maxCount;
  private final Long taggerId;

  @Builder
  private BaseballCondition(
      Long roomId, String currentBall, int ballCount, int maxCount, Long taggerId
  ) {
    this.roomId = roomId;
    this.currentBall = currentBall;
    this.ballCount = ballCount;
    this.maxCount = maxCount;
    this.taggerId = taggerId;
  }

  public BaseballCondition withBall(String ball) {
    return BaseballCondition.builder()
        .roomId(roomId)
        .currentBall(ball)
        .ballCount(ballCount)
        .maxCount(maxCount)
        .taggerId(taggerId)
        .build();
  }

  public boolean isHomeRun(String trial) {
    return Objects.equals(trial, currentBall);
  }

  public boolean isOver(int currentCount) {
    return currentCount == maxCount;
  }

  public String attempt(boolean isOver, String trial) {
    if (isOver) {
      return "모든 시도가 끝났습니다! 술래 +35";
    }

    if (isHomeRun(trial)) {
      return "정답입니다! +35";
    }

    int strikeCount = 0;
    int ballCount = 0;

    for (int i = 0; i < trial.length(); i++) {
      String num = String.valueOf(trial.charAt(i));

      if (currentBall.contains(num)) {
        if (currentBall.indexOf(num) == i) {
          strikeCount++;
        } else {
          ballCount++;
        }
      }
    }

    return strikeCount + " 스트라이크, " + ballCount + " 볼!";
  }

}
