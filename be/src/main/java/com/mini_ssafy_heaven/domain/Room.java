package com.mini_ssafy_heaven.domain;

import org.apache.commons.lang3.StringUtils;
import com.mini_ssafy_heaven.domain.enums.RoomStatus;
import com.mini_ssafy_heaven.global.exception.code.RoomErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Room {
  
  private static final int MAX_TITLE_LENGTH = 20;
  
  private Long id;
  private final String title;
  
  @Getter(AccessLevel.NONE)
  private final RoomStatus status;
  
  @Builder
  private Room(String title) {
    validate(title);
    
    this.title = title;
    this.status = RoomStatus.CREATING;
  }

  public String getRoomStatus() {
    return status.getStatus();
  }
  
  private void validate(String title) {
    if (StringUtils.isBlank(title)) {
      throw new IllegalArgumentException(RoomErrorCode.EMPTY_TITLE.getMessage());
    }
    
    if (title.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(RoomErrorCode.EXCESSIVE_TITLE_LENGTH.getMessage());
    }
  }
  
}
