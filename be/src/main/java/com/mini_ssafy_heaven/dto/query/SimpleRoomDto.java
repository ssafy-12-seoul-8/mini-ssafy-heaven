package com.mini_ssafy_heaven.dto.query;

public record SimpleRoomDto(
    Long id,
    String title,
    Integer capacity,
    String manager
) {

}
