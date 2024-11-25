package com.mini_ssafy_heaven.dto.response;

import java.util.List;

public record ScrollResponse<T>(
    boolean isEmpty,
    boolean hasNext,
    List<T> contents,
    Long nextCursor
) {

  public static <T> ScrollResponse<T> from(boolean hasNext, List<T> contents, Long nextCursor) {
    return new ScrollResponse<T>(contents.isEmpty(), hasNext, contents, nextCursor);
  }

}
