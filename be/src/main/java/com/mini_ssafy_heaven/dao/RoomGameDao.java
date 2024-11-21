package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.RoomGame;
import com.mini_ssafy_heaven.dto.response.RoomGameTitleDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomGameDao {

  int save(
      @Param("roomGame")
      RoomGame roomGame
  );

  List<RoomGameTitleDto> findTitlesByRoomId(Long roomId);

}
