package com.mini_ssafy_heaven.doc;

import com.mini_ssafy_heaven.dto.response.GameDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

@Tag(
    name = "게임 API",
    description = "게임 관련 API"
)
public interface GameDocument {

  @Operation(summary = "게임 전체 조회")
  @ApiResponse(
      responseCode = "200",
      description = "OK"
  )
  ResponseEntity<List<GameDetailResponse>> getAll();

}
