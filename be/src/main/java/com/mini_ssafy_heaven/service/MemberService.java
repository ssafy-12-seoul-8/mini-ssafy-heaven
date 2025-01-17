package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.request.LoginMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.dto.response.GuestLoginResponse;
import com.mini_ssafy_heaven.dto.response.LoginMemberResponse;
import com.mini_ssafy_heaven.dto.response.MemberInfoResponse;

public interface MemberService {

  boolean existsId(CreateMemberRequest request);

  CreateMemberResponse addMember(CreateMemberRequest request);

  LoginMemberResponse login(LoginMemberRequest request);

  MemberInfoResponse getMemberById(Long id);

  GuestLoginResponse loginGuest();

  void validateId(Long loginId);
}
