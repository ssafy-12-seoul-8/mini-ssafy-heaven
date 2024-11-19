package com.mini_ssafy_heaven.doc;

import com.mini_ssafy_heaven.doc.example.MemberErrorExample;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원 API", description = "회원 관련 API")
public interface MemberDocument {

  @Operation(summary = "회원 생성")
  @ApiResponses(
    {@ApiResponse(responseCode = "201", description = "OK"),
        @ApiResponse(
          responseCode = "409",
          description = "CONFLICT",
          content = @Content(
            examples = @ExampleObject(
              description = "이미 존재하는 회원일 때",
              value = MemberErrorExample.EXISTING_MEMBER
            )
          )
        )}
  )
  ResponseEntity<CreateMemberResponse> addMember(CreateMemberRequest request);

}
