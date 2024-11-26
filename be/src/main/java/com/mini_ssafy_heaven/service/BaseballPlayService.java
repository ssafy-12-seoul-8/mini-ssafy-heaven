package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.BaseballCondition;
import com.mini_ssafy_heaven.domain.DescriptionReadCount;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.domain.enums.GameMessageType;
import com.mini_ssafy_heaven.domain.enums.GameType;
import com.mini_ssafy_heaven.dto.response.DescriptionReadResponse;
import com.mini_ssafy_heaven.dto.response.GameResponse;
import com.mini_ssafy_heaven.global.exception.code.InGameErrorCode;
import com.mini_ssafy_heaven.repository.BaseballConditionRepository;
import com.mini_ssafy_heaven.repository.DescriptionReadCountRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseballPlayService implements GamePlayService<BaseballCondition> {

  private final RoomPlayerDao roomPlayerDao;
  private final BaseballConditionRepository baseballConditionRepository;
  private final DescriptionReadCountRepository descriptionReadCountRepository;

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

  @Override
  public GameResponse<DescriptionReadResponse> readDescription(Long roomId, int totalCount) {
    DescriptionReadCount readCount = descriptionReadCountRepository.findById(roomId)
        .orElseGet(() -> new DescriptionReadCount(roomId, 0));
    DescriptionReadCount incremented = readCount.increment();

    descriptionReadCountRepository.save(incremented);

    DescriptionReadResponse response = new DescriptionReadResponse(incremented.getReadCount());

    if (incremented.getReadCount() >= totalCount) {
      descriptionReadCountRepository.deleteById(roomId);
    }

    return new GameResponse<>(GameType.BASEBALL, GameMessageType.CONFIRM, response);
  }

  @Override
  public GameResponse<Void> roundStart() {

    return new GameResponse<>(GameType.BASEBALL, GameMessageType.ROUND_START, null);
  }

  @Override
  @Transactional
  public GameResponse<BaseballCondition> setAnswer(Long roomId, String answer) {
    BaseballCondition condition = baseballConditionRepository.findById(roomId)
        .orElseThrow(
            () -> new NoSuchElementException(InGameErrorCode.NO_GAME_FOR_ROOM.getMessage()));
    BaseballCondition updated = condition.withBall(answer);

    baseballConditionRepository.save(updated);

    return new GameResponse<>(GameType.BASEBALL, GameMessageType.SET_ANSWER, updated);
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
