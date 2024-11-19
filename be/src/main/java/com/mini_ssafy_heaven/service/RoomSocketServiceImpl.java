package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.dao.PlayerCountRepository;
import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.Member;
import com.mini_ssafy_heaven.domain.RoomPlayer;
import com.mini_ssafy_heaven.domain.RoomPlayerCount;
import com.mini_ssafy_heaven.domain.enums.SocketMessageType;
import com.mini_ssafy_heaven.dto.request.MessageRequest;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import com.mini_ssafy_heaven.global.annotation.Lock;
import com.mini_ssafy_heaven.global.exception.code.MemberErrorCode;
import com.mini_ssafy_heaven.global.exception.code.RoomPlayerCountErrorCode;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomSocketServiceImpl implements RoomSocketService {

  private final PlayerCountRepository playerCountRepository;
  private final RoomPlayerDao roomPlayerDao;
  private final MemberDao memberDao;

  @Override
  public MessageResponse handleMessage(Long roomId, MessageRequest request) {
    return switch (request.type()) {
      case ENTER -> handleJoin(roomId, request);
      default -> null;
    };
  }

  @Lock("player-join")
  private MessageResponse handleJoin(Long roomId, MessageRequest request) {
    Long memberId = request.memberId();
    SocketMessageType type = request.type();
    RoomPlayerCount playerCount = playerCountRepository.findById(roomId)
        .orElseThrow(
          () -> new NoSuchElementException(
            RoomPlayerCountErrorCode.UNEXPECTED_EMPTY_ROOM.getMessage()
          )
        );

    playerCount.validateJoinability();
    increaseCount(playerCount, memberId, roomId);

    RoomPlayer roomPlayer = RoomPlayer.createPlayer(memberId, roomId);

    roomPlayerDao.save(roomPlayer);

    String message = getEnterMessage(memberId, type);

    return new MessageResponse(type, message);
  }



  private void increaseCount(RoomPlayerCount playerCount, Long memberId, Long roomId) {
    RoomPlayerCount increased = playerCount.increase();

    playerCountRepository.save(increased);
  }

  private String getEnterMessage(Long memberId, SocketMessageType type) {
    Member member = memberDao.findById(memberId)
        .orElseThrow(
          () -> new NoSuchElementException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
        );

    return type.format(member.getNickname());
  }

}
