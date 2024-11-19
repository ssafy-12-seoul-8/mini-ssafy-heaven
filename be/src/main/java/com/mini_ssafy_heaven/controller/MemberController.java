package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.doc.MemberDocument;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.service.MemberService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController implements MemberDocument {

  private final MemberService memberService;

  @Override
  @PostMapping
  public ResponseEntity<CreateMemberResponse> addMember(@RequestBody
  CreateMemberRequest request) {

    CreateMemberResponse response = memberService.addMember(request);
    URI uri = URI.create("/api/members/" + response.id());

    return ResponseEntity.created(uri)
        .body(response);
  }

}
