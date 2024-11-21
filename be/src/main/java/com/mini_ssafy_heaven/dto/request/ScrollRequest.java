package com.mini_ssafy_heaven.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.Objects;

public record ScrollRequest(
    @Parameter(
        name = "cursor",
        description = "검색 용 커서. 미입력/초과입력 시 가장 최근 조회",
        example = "10"
    )
    Long cursor,

    @Parameter(
        name = "size",
        description = "검색 결과 리스트 사이즈. 기본 10개",
        example = "20"
    )
    Integer size
) {

  private static final Integer DEFAULT_SIZE = 10;

  public ScrollRequest(
      Long cursor,
      Integer size
  ) {
    this.cursor = cursor;
    this.size = Objects.requireNonNullElse(size, DEFAULT_SIZE);
  }

}
