package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.request.LoginMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.dto.response.GuestLoginResponse;
import com.mini_ssafy_heaven.dto.response.LoginMemberResponse;

public interface MemberService {

  boolean existsId(CreateMemberRequest request);

  CreateMemberResponse addMember(CreateMemberRequest request);

  LoginMemberResponse login(LoginMemberRequest request);

  GuestLoginResponse loginGuest();

}
