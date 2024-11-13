package com.mini_ssafy_heaven.dao;

import java.util.List;

public interface GameDao {
  
  List<Long> findIdsIn(List<Long> ids);

}
