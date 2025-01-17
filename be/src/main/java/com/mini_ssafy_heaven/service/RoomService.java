package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.ScrollRequest;
import com.mini_ssafy_heaven.dto.request.UpdateRoomStatusRequest;
import com.mini_ssafy_heaven.dto.response.BasicRoomResponse;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.dto.response.RoomDetailResponse;
import com.mini_ssafy_heaven.dto.response.ScrollResponse;

public interface RoomService {

  CreateRoomResponse create(CreateRoomRequest request, Long loginId);

  ScrollResponse<BasicRoomResponse> getAll(ScrollRequest request);

  void join(Long roomId, Long loginId);

  void updateStatus(Long id, UpdateRoomStatusRequest request, Long loginId);

  RoomDetailResponse getDetail(Long id, Long loginId);

}
