package com.mini_ssafy_heaven.dao;

import com.mini_ssafy_heaven.domain.RoomPlayerCount;
import org.springframework.data.repository.CrudRepository;

public interface PlayerCountRepository extends CrudRepository<RoomPlayerCount, Long> {

}
