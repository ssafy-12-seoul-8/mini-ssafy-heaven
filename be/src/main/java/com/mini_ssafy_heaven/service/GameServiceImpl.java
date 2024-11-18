package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.GameDao;
import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameDao gameDao;

  @Override
  public List<GameDetailResponse> getAll() {
    List<GameDetailResponse> games = gameDao.findAll();

    return Collections.unmodifiableList(games);
  }

}
