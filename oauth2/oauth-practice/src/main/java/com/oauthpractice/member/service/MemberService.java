package com.oauthpractice.member.service;

import com.oauthpractice.member.domain.Member;
import com.oauthpractice.member.domain.SocialType;
import com.oauthpractice.member.dto.MemberCreateDto;
import com.oauthpractice.member.dto.MemberLoginDto;
import com.oauthpractice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(MemberCreateDto memberCreateDto) {
        Member member = Member.builder()
                .email(memberCreateDto.getEmail())
                .password(passwordEncoder.encode(memberCreateDto.getPassword()))
                .build();
        return memberRepository.save(member);
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Member foundMember = memberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), foundMember.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return foundMember;
    }

    public Member getMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId).orElse(null);
    }

    public Member createOauth(String socialId, String email, SocialType socialType) {
        Member member = Member.builder()
                .email(email)
                .socialType(socialType)
                .socialId(socialId)
                .build();
        memberRepository.save(member);
        return member;
    }
}
