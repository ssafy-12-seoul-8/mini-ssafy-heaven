package com.mini_ssafy_heaven.dao;

import java.util.List;
import java.util.Set;

public interface GameDao {

  Set<Long> findIdsIn(List<Long> ids);

}
