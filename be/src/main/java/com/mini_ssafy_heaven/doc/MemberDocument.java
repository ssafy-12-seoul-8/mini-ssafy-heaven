package com.mini_ssafy_heaven.doc;

import org.springframework.http.ResponseEntity;

import com.mini_ssafy_heaven.doc.example.RoomErrorExample;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@Tag(name = "회원 API", description = "회원 관련 API")
public interface MemberDocument {

  @Operation(summary = "회원 생성")
  @ApiResponses(
      {
        @ApiResponse(
            responseCode = "201",
            description = "OK"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "CONFLICT",
            content = @Content(
                examples = @ExampleObject(
                    description = "방장이 다른 방에 참여하고 있을 때",
                    value = RoomErrorExample.JOINED_PLAYER
                )
            )
        )
    }
  )
  ResponseEntity<CreateMemberResponse> addMember(CreateMemberRequest request, HttpSession session);

}
