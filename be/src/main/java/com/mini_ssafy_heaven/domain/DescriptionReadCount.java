package com.mini_ssafy_heaven.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.runtime.Desc;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("ROOM-READY")
@RequiredArgsConstructor
@Getter
public class DescriptionReadCount {

  @Id
  private final Long roomId;
  private final int readCount;

  public DescriptionReadCount increment() {
    return new DescriptionReadCount(this.roomId, this.readCount + 1);
  }

}
