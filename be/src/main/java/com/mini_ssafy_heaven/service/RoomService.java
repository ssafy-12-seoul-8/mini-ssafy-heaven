package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.UpdateRoomStatusRequest;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;

public interface RoomService {

  CreateRoomResponse create(CreateRoomRequest request, Long loginId);

  void join(Long roomId, Long loginId);

  void updateStatus(Long id, UpdateRoomStatusRequest request, Long loginId);

}
