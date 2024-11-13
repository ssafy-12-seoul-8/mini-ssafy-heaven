package com.mini_ssafy_heaven.dao;

import org.apache.ibatis.annotations.Param;
import com.mini_ssafy_heaven.domain.RoomPlayer;

public interface RoomPlayerDao {
  
  boolean existsByMemberId(Long id);

  int save(@Param("roomPlayer") RoomPlayer roomPlayer);
  
}
