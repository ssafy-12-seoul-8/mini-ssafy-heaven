package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.Room;
import com.mini_ssafy_heaven.dto.query.SimpleRoomDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoomDao {

  int save(@Param("room") Room room);

  boolean existsById(Long id);

  List<SimpleRoomDto> selectAll(@Param("cursor") Long cursor, @Param("size") Integer size);

}
