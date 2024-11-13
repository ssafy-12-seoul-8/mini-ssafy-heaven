package com.mini_ssafy_heaven.dao;

import org.apache.ibatis.annotations.Param;
import com.mini_ssafy_heaven.domain.RoomGame;

public interface RoomGameDao {
  
  int save(@Param("roomGame") RoomGame roomGame);

}
