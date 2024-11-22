package com.mini_ssafy_heaven.doc;

import com.mini_ssafy_heaven.doc.example.RoomErrorExample;
import com.mini_ssafy_heaven.dto.request.CreateRoomRequest;
import com.mini_ssafy_heaven.dto.request.ScrollRequest;
import com.mini_ssafy_heaven.dto.request.UpdateRoomStatusRequest;
import com.mini_ssafy_heaven.dto.response.BasicRoomResponse;
import com.mini_ssafy_heaven.dto.response.CreateRoomResponse;
import com.mini_ssafy_heaven.dto.response.RoomDetailResponse;
import com.mini_ssafy_heaven.dto.response.ScrollResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;

@Tag(name = "방 API", description = "방 관련 API")
public interface RoomDocument {

  @Operation(summary = "방 생성")
  @ApiResponses(
    {@ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(
          responseCode = "409",
          description = "CONFLICT",
          content = @Content(
            examples = @ExampleObject(
              description = "방장이 다른 방에 참여하고 있을 때",
              value = RoomErrorExample.JOINED_PLAYER
            )
          )
        )}
  )
  ResponseEntity<CreateRoomResponse> create(CreateRoomRequest request, HttpSession session);

  @Operation(summary = "방 전체 조회")
  @ApiResponses(
    {@ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)}
  )
  ResponseEntity<ScrollResponse<BasicRoomResponse>> getAll(@ParameterObject ScrollRequest request);

  @Operation(summary = "방 상세 조회")
  @ApiResponse(responseCode = "200", description = "OK")
  ResponseEntity<RoomDetailResponse> getDetail(Long id, HttpSession session);

  @Operation(summary = "방 참여")
  @ApiResponses(@ApiResponse(responseCode = "201", description = "CREATED"))
  ResponseEntity<Void> join(Long roomId, HttpSession session);

  @Operation(summary = "방 상태 갱신")
  @ApiResponse(responseCode = "204", description = "NO CONTENT")
  ResponseEntity<Void> updateStatus(Long id, UpdateRoomStatusRequest request, HttpSession session);

}
