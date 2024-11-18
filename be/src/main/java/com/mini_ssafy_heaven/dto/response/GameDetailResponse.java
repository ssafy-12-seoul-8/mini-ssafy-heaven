package com.mini_ssafy_heaven.dto.response;

public record GameDetailResponse(
    Long id,
    String title,
    String description,
    Integer maxRound
) {

}
