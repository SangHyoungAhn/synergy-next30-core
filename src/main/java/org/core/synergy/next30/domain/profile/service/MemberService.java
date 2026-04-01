package org.core.synergy.next30.domain.profile.service;


import lombok.RequiredArgsConstructor;
import org.core.synergy.next30.domain.profile.entity.Member;
import org.core.synergy.next30.domain.profile.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //특정 사용자의 전체 프로필 정보 조회
    public Member getMemberProfile(String loginId) {
        return memberRepository.findById(loginId)
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
    }

}
