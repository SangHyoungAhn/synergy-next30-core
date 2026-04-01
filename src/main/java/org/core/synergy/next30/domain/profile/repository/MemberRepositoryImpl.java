package org.core.synergy.next30.domain.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.core.synergy.next30.domain.profile.entity.Member;


import java.util.List;

import static org.core.synergy.next30.domain.profile.entity.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> searchMembers(String name, String deptName, String empMbti) {
        return queryFactory
                .selectFrom(member)
                .where(

                )
                .fetch();
    }
}
