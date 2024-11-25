package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.BaseballCondition;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.domain.enums.GameMessageType;
import com.mini_ssafy_heaven.domain.enums.GameType;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.repository.BaseballConditionRepository;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseballPlayService implements GamePlayService<BaseballCondition> {

  private final RoomPlayerDao roomPlayerDao;
  private final BaseballConditionRepository baseballConditionRepository;

  @Override
  @Transactional
  public GameResponse<BaseballCondition> start(Long roomId) {
    List<RoomPlayer> players = roomPlayerDao.findAllByRoomId(roomId);
    int ballCount = calculateBallDigits(players.size());
    int maxCount = calculateMaxCount(players.size());
    RoomPlayer tagger = decideTagger(players);
    BaseballCondition condition = BaseballCondition.builder()
        .roomId(roomId)
        .ballCount(ballCount)
        .maxCount(maxCount)
        .taggerId(tagger.getMemberId())
        .build();

    baseballConditionRepository.save(condition);

    return new GameResponse<>(GameType.BASEBALL, GameMessageType.START, condition);
  }

  private static int calculateBallDigits(int playerCount) {
    return playerCount > 3 ? 4 : 3;
  }

  private static int calculateMaxCount(int playerCount) {
    return playerCount % 2 == 0 ? 6 : 4;
  }

  private static RoomPlayer decideTagger(List<RoomPlayer> players) {
    Random random = new Random();
    int randomIndex = random.nextInt(0, players.size());

    return players.get(randomIndex);
  }

}
