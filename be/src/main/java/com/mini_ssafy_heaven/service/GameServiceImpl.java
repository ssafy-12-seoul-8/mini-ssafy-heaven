package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.GameDao;
import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import com.mini_ssafy_heaven.global.exception.code.GameErrorCode;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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

  @Override
  public GameDetailResponse getById(Long id) {
    return gameDao.findById(id)
        .orElseThrow(() -> new NoSuchElementException(GameErrorCode.GAME_NOT_FOUND.getMessage()));
  }

}
