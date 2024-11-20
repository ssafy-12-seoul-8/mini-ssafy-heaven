package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.Room;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface RoomDao {

  int save(@Param("room") Room room);

  boolean existsById(Long id);

  Optional<Room> findById(Long id);

  void update(@Param("room") Room room);

}
