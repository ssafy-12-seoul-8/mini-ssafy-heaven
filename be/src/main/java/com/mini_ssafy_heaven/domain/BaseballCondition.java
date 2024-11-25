package com.mini_ssafy_heaven.domain;

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

}
