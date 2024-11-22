package com.mini_ssafy_heaven.domain;

import com.mini_ssafy_heaven.domain.enums.MemberRole;
import com.mini_ssafy_heaven.domain.enums.RandomUserAdjective;
import com.mini_ssafy_heaven.domain.enums.RandomUserAnimal;
import com.mini_ssafy_heaven.global.exception.code.MemberErrorCode;
import io.micrometer.common.util.StringUtils;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

  private static final int MAX_NAME_LENGTH = 30;
  private static final int MAX_PASSWORD_LENGTH = 100;

  private final Long id;
  private final String username;
  private final String password;
  private final String nickname;
  private final MemberRole role;
  private final Integer score;

  @Builder
  public Member(
    Long id,
    String username,
    String password,
    String nickname,
    MemberRole role,
    Integer score
  ) {
    validateName(username);
    validatePassword(password);
    validateName(nickname);

    this.id = id;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.role = Objects.isNull(role) ? MemberRole.NORMAL : role;
    this.score = score;
  }

  public static Member createGuest() {
    String uuid = UUID.randomUUID()
        .toString();
    String lowTime = uuid.substring(0, 8);
    RandomUserAdjective adjective = RandomUserAdjective.getRandom();
    RandomUserAnimal animal = RandomUserAnimal.getRandom();
    String username = adjective.getUsername() + animal.getUsername() + lowTime;
    String nickname = adjective.getNickname() + " " + animal.getNickname() + lowTime.substring(0, 4);
    String password = uuid.substring(8, uuid.length());

    return Member.builder()
        .username(username)
        .password(password)
        .nickname(nickname)
        .role(MemberRole.GUEST)
        .score(0)
        .build();
  }

  private void validateName(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException(MemberErrorCode.EMPTY.getMessage());
    }

    if (name.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(MemberErrorCode.EXCESSIVE_NAME_LENGTH.getMessage());
    }
  }

  private void validatePassword(String password) {
    if (StringUtils.isBlank(password)) {
      throw new IllegalArgumentException(MemberErrorCode.EMPTY.getMessage());
    }

    if (password.length() > MAX_PASSWORD_LENGTH) {
      throw new IllegalArgumentException(MemberErrorCode.EXCESSIVE_PASSWORD_LENGTH.getMessage());
    }
  }

}
