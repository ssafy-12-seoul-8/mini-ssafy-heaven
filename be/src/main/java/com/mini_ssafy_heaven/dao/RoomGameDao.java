package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.RoomGame;
import org.apache.ibatis.annotations.Param;

public interface RoomGameDao {

  int save(
      @Param("roomGame")
      RoomGame roomGame
  );

}
