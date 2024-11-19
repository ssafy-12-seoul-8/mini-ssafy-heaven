package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;

public interface MemberService {

  boolean existsId(CreateMemberRequest request);

  CreateMemberResponse addMember(CreateMemberRequest request);
}
