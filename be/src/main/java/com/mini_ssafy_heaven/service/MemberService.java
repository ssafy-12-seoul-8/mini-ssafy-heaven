package com.mini_ssafy_heaven.service;

import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;

public interface MemberService {
  
  boolean existsId(CreateMemberRequest request);
  boolean addMember(CreateMemberRequest request);
}
