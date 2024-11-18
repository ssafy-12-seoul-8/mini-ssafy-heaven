package com.mini_ssafy_heaven.fixture;

import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.domain.enums.RoomStatus;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoomFixture extends BaseFixture {

  public static Room createAllRandom() {
    return createRandom(null, null, null);
  }

  public static Room createRandom(String title, Integer capacity, RoomStatus status) {
    title = Objects.isNull(title) ? getRandomSentence(1, 20) : title;
    capacity = Objects.isNull(capacity) ? getRandomInt(1, 5) : capacity;

    return create(title, capacity, status, null);
  }

  public static Room create(
      String title,
      Integer capacity,
      RoomStatus status,
      String statusString
  ) {
    return Room.builder()
        .title(title)
        .capacity(capacity)
        .status(status)
        .statusString(statusString)
        .build();
  }

}
