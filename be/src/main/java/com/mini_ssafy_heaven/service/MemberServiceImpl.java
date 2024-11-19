package com.mini_ssafy_heaven.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mini_ssafy_heaven.dao.MemberDao;
import com.mini_ssafy_heaven.domain.Member;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.global.exception.code.MemberErrorCode;
import lombok.RequiredArgsConstructor;

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

  // 아이디 중복확인
  private void validateUserName(String username) {
    if (memberDao.existsByUserName(username)) {
      throw new DuplicateKeyException(MemberErrorCode.Existing_User.getMessage());
    }
  }

}
