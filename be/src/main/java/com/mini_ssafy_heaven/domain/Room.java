package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.RoomStatus;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import java.util.Objects;
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
  private final RoomStatus status;

  private Room(Long id, String title, Integer capacity, RoomStatus status) {
    this(id, title, capacity, status, null);
  }

  @Builder
  private Room(
      Long id,
      String title,
      Integer capacity,
      RoomStatus status,
      String statusString
  ) {
    validate(title, capacity);

    this.id = id;
    this.title = title;
    this.capacity = Objects.nonNull(capacity) ? capacity : MAX_CAPACITY;
    this.status = Objects.nonNull(status) ? status : RoomStatus.get(statusString);
  }

  public boolean isManagedBy(Long memberId) {
    return this.id.equals(memberId);
  }

  public Room updateStatus(String status) {
    if (Objects.isNull(status)) {
      throw new IllegalArgumentException(RoomErrorCode.NULL_STATUS_FOR_UPDATE.getMessage());
    }

    // TODO: 방 상태 관련 커밋 합치고 valueOf 수정
    return update(this.title, this.capacity, RoomStatus.valueOf(status));
  }

  private Room update(String title, Integer capacity, RoomStatus status) {
    if (Objects.isNull(title)) {
      title = this.title;
    }

    if (Objects.isNull(capacity)) {
      capacity = this.capacity;
    }

    if (Objects.isNull(status)) {
      status = this.status;
    }

    return Room.builder()
        .id(this.id)
        .title(title)
        .capacity(capacity)
        .status(status)
        .build();
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
