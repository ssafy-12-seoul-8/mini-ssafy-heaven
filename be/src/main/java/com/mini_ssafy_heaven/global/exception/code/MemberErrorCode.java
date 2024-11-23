package com.mini_ssafy_heaven.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberErrorCode {
  Existing_User("이미 존재하는 사용자입니다."),
  NON_EXISTENT_USER("존재하지 않는 사용자입니다."),
  WRONG_PASSWORD("비밀번호가 다릅니다."),
  EXCESSIVE_NAME_LENGTH("최대 30자입니다."),
  EXCESSIVE_PASSWORD_LENGTH("비밀번호는 최대 100자입니다."),
  EMPTY("필수값입니다."),
  MEMBER_NOT_FOUND("해당 아이디의 회원이 존재하지 않습니다.");

  private final String message;

}
