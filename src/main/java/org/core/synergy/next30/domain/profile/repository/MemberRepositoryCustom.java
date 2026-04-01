package org.core.synergy.next30.domain.profile.repository;

import org.core.synergy.next30.domain.profile.entity.Member;

import java.util.List;

//QueryDSL 메서드 정의하는 인터페이스
public interface MemberRepositoryCustom {
    List<Member> searchMembers(String name, String deptName, String empMbti);
}
