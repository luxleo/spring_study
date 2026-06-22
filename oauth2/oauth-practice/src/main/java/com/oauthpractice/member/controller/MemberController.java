package com.oauthpractice.member.controller;

import com.oauthpractice.common.auth.JwtTokenProvider;
import com.oauthpractice.member.domain.Member;
import com.oauthpractice.member.domain.SocialType;
import com.oauthpractice.member.dto.*;
import com.oauthpractice.member.service.GoogleService;
import com.oauthpractice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleService googleService;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberCreateDto memberCreateDto){
        Member member = memberService.create(memberCreateDto);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }
    @PostMapping("/doLogin")
    public ResponseEntity<?> doLogin(@RequestBody MemberLoginDto memberLoginDto){
        Member member = memberService.login(memberLoginDto);
        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", member.getId());
        loginInfo.put("token", token);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
    @PostMapping("/google/doLogin")
    public ResponseEntity<?> googleLogin(@RequestBody RedirectDto redirectDto){
//        accesstoken 발급
        AccessTokenDto accessTokenDto = googleService.getAccessToken(redirectDto.getCode());
//        사용자정보 얻기
        GoogleProfileDto googleProfileDto = googleService.getGoogleProfile(accessTokenDto.getAccess_token());
//        회원가입이 되어 있지 않다면 회원가입
        Member originalMember = memberService.getMemberBySocialId(googleProfileDto.getSub());
        if(originalMember == null){
            originalMember = memberService.createOauth(googleProfileDto.getSub(), googleProfileDto.getEmail(), SocialType.GOOGLE);
        }
//        회원가입돼 있는 회원이라면 토큰발급
        String jwtToken = jwtTokenProvider.createToken(originalMember.getEmail(), originalMember.getRole().toString());

        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", originalMember.getId());
        loginInfo.put("token", jwtToken);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }
}
