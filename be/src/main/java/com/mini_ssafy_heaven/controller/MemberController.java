package com.mini_ssafy_heaven.controller;

import com.mini_ssafy_heaven.doc.MemberDocument;
import com.mini_ssafy_heaven.domain.Member;
import com.mini_ssafy_heaven.dto.request.CreateMemberRequest;
import com.mini_ssafy_heaven.dto.request.LoginMemberRequest;
import com.mini_ssafy_heaven.dto.response.CreateMemberResponse;
import com.mini_ssafy_heaven.dto.response.LoginMemberResponse;
import com.mini_ssafy_heaven.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  @PostMapping("/signUp")
  public ResponseEntity<CreateMemberResponse> addMember(@RequestBody CreateMemberRequest request) {
    CreateMemberResponse response = memberService.addMember(request);
    URI uri = URI.create("/api/members/" + response.id());

    return ResponseEntity.created(uri)
        .body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginMemberResponse> login(
    @RequestBody LoginMemberRequest request,
    HttpSession session
  ) {
    LoginMemberResponse response = memberService.login(request);
    session.setAttribute("loginId", response.id());
    URI uri = URI.create("/api/members/" + response.id());

    return ResponseEntity.created(uri)
        .body(response);
  }

  @GetMapping("/me")
  public ResponseEntity<Member> getMe(HttpSession session) {
    Long loginId = (long) session.getAttribute("loginId");
    Member member = memberService.getMemberById(loginId);
    URI uri = URI.create("/api/members/" + member.getId());

    return ResponseEntity.ok(member);
  }

}
