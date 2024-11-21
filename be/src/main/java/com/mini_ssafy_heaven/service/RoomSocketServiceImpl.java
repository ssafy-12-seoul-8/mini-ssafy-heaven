package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.RoomPlayerDao;
import com.mini_ssafy_heaven.domain.enums.SocketMessageType;
import com.mini_ssafy_heaven.dto.query.RoomPlayerNameDto;
import com.mini_ssafy_heaven.dto.request.EnterRequest;
import com.mini_ssafy_heaven.dto.response.EnterResponse;
import com.mini_ssafy_heaven.dto.response.MessageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomSocketServiceImpl implements RoomSocketService {

  private final RoomPlayerDao roomPlayerDao;

  @Override
  public MessageResponse<EnterResponse> enter(Long roomId, EnterRequest request) {
    List<RoomPlayerNameDto> players = roomPlayerDao.findAllByRoomId(roomId);
    EnterResponse response = EnterResponse.from(players, request.nickname());

    return MessageResponse.from(SocketMessageType.ENTER, response);
  }

}
