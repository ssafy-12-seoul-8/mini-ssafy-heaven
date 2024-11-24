package com.mini_ssafy_heaven.dto.response;

import com.mini_ssafy_heaven.domain.Member;
import lombok.Builder;

@Builder
public record GuestLoginResponse(Long id, String nickname) {

  public static GuestLoginResponse from(Member member) {
    return GuestLoginResponse.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .build();
  }

}
