package com.mini_ssafy_heaven.fixture;

import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.domain.enums.RoomPlayerRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoomPlayerFixture extends BaseFixture {

  public static RoomPlayer createWithRoomAndMember(
      Long roomId,
      Long memberId
  ) {
    return create(roomId, memberId, null, null);
  }

  public static RoomPlayer createManager(
      Long roomId,
      Long memberId
  ) {
    return create(roomId, memberId, RoomPlayerRole.MANAGER, null);
  }

  public static RoomPlayer create(
      Long roomId,
      Long memberId,
      RoomPlayerRole role,
      String roleString
  ) {
    return RoomPlayer.builder()
        .roomId(roomId)
        .memberId(memberId)
        .role(role)
        .roleString(roleString)
        .build();
  }

}
