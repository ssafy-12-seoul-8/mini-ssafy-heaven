package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.RoomPlayer;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface RoomPlayerDao {

  boolean existsByMemberId(Long id);

  int save(@Param("roomPlayer") RoomPlayer roomPlayer);

  int countByRoomId(Long roomId);

  Optional<RoomPlayer> findByRoomAndMember(
    @Param("roomId") Long roomId,
    @Param("memberId") Long memberId
  );

}
