package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.Room;
import org.apache.ibatis.annotations.Param;

public interface RoomDao {

  int save(
      @Param("room")
      Room room
  );

}
