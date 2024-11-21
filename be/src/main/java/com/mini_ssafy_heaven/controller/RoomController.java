package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.doc.RoomDocument;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.ScrollRequest;
import com.mini_ssafy_heaven.dto.response.BasicRoomResponse;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.dto.response.ScrollResponse;
import com.mini_ssafy_heaven.service.RoomService;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController implements RoomDocument {

  private final RoomService roomService;

  @Override
  @PostMapping
  public ResponseEntity<CreateRoomResponse> create(
    @RequestBody CreateRoomRequest request,
    HttpSession session
  ) {
    // TODO: 로그인 기능 생성 후 실 사용자로 바꾸기
    Long loginId = 1L;
    CreateRoomResponse response = roomService.create(request, loginId);
    URI uri = URI.create("/api/rooms/" + response.id());

    return ResponseEntity.created(uri)
        .body(response);
  }

  @Override
  @GetMapping
  public ResponseEntity<ScrollResponse<BasicRoomResponse>> getAll(ScrollRequest request) {
    ScrollResponse<BasicRoomResponse> response = roomService.getAll(request);

    return ResponseEntity.ok(response);
  }


  @Override
  @PostMapping("/{id}")
  public ResponseEntity<Void> join(@PathVariable("id") Long id, HttpSession session) {
    // TODO: 로그인 기능 생성 후 실 사용자로 바꾸기
    Long loginId = 1L;

    roomService.join(id, loginId);

    return ResponseEntity.noContent()
        .build();
  }

}
