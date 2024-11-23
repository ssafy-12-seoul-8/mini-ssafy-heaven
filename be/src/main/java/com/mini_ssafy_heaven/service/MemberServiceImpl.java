package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.domain.Member;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.request.LoginMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.dto.response.LoginMemberResponse;
import com.mini_ssafy_heaven.global.exception.code.MemberErrorCode;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberDao memberDao;

  @Override
  public boolean existsId(CreateMemberRequest request) {
    // TODO Auto-generated method stub
    return false;
  }

  // 회원 추가
  @Override
  @Transactional
  public CreateMemberResponse addMember(CreateMemberRequest request) {
    validateUserName(request.username());

    Member member = Member.builder()
        .username(request.username())
        .password(request.password())
        .nickname(request.nickname())
        .build();

    memberDao.addMember(member);

    return new CreateMemberResponse(member.getId());
  }

  @Override
  public LoginMemberResponse login(LoginMemberRequest request) {
    Member member = memberDao.getMemberInfo(request.username())
        .orElseThrow(
          () -> new NoSuchElementException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
        );

    member.verifyPassword(request.password());

    return new LoginMemberResponse(member.getId());
  }

  @Override
  public Member getMemberById(Long id) {
    Member member = memberDao.findById(id)
        .orElseThrow(
          () -> new NoSuchElementException(MemberErrorCode.MEMBER_NOT_FOUND.getMessage())
        );

    return member;
  }

  // 아이디 중복확인
  private void validateUserName(String username) {
    if (memberDao.existsByUserName(username)) {
      throw new DuplicateKeyException(MemberErrorCode.Existing_User.getMessage());
    }
  }

}
