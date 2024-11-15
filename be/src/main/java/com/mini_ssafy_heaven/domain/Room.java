package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.RoomStatus;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Room {

  private static final int MAX_TITLE_LENGTH = 20;
  private static final int MAX_CAPACITY = 5;

  private final Long id;
  private final String title;
  private final Integer capacity;

  @Getter(AccessLevel.NONE)
  private final RoomStatus status;

  @Builder
  private Room(
      Long id,
      String title,
      Integer capacity,
      RoomStatus status
  ) {
    validate(title, capacity);

    this.id = id;
    this.title = title;
    this.capacity = Objects.nonNull(capacity) ? capacity : MAX_CAPACITY;
    this.status = Objects.nonNull(status) ? status : RoomStatus.CREATING;
  }

  public String getStatus() {
    return status.getStatus();
  }

  private void validate(String title, Integer capacity) {
    validateTitle(title);

    if (Objects.nonNull(capacity)) {
      validateCapacity(capacity);
    }
  }

  private void validateTitle(String title) {
    if (StringUtils.isBlank(title)) {
      throw new IllegalArgumentException(RoomErrorCode.EMPTY_TITLE.getMessage());
    }

    if (title.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(RoomErrorCode.EXCESSIVE_TITLE_LENGTH.getMessage());
    }
  }

  private void validateCapacity(Integer capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException(RoomErrorCode.POSITIVE_CAPACITY_REQUIRED.getMessage());
    }

    if (capacity > MAX_CAPACITY) {
      throw new IllegalArgumentException(RoomErrorCode.EXCESSIVE_CAPACITY.getMessage());
    }
  }

}
