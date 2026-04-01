package org.core.synergy.next30.domain.profile.repository;

import jakarta.persistence.EntityManager;
import org.core.synergy.next30.domain.profile.entity.Member;
import org.core.synergy.next30.domain.profile.entity.MemberDetail;
import org.core.synergy.next30.global.config.QuerydslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("멤버와 상세정보가 일대일로 매핑되어 저장되어야 한다")

    void saveMemberWithDetail() {
        // given
        Member member = Member.builder()
                .loginId("shahn0718")
                .empCode("C019005")
                .empName("안상형")
                .compCode("1400")
                .deptCode("C101225")
                .deptName("비즈테크팀")
                .posName("대리")
                .posCode("EC3")
                .dutyName("-")
                .dutyCode("C10")
                .build();

        MemberDetail detail = MemberDetail.builder()
                .member(member)
                .email("shahn0718@donga.com")
                .telPhone("01031984329")
                .empMbti("INTJ")
                .empAsset("JAVA SPRING")
                .build();
        // when
        member.setMemberDetail(detail);
        memberRepository.save(member);

        em.flush();
        em.clear();
        // then
        Member savedMember = memberRepository.findById("shahn0718")
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        assertThat(savedMember.getEmpName()).isEqualTo("안상형");
        assertThat(savedMember.getDeptName()).isEqualTo("비즈테크팀");

        assertThat(savedMember.getMemberDetail()).isNotNull();
        assertThat(savedMember.getMemberDetail().getEmpMbti()).isEqualTo("INTJ");
        assertThat(savedMember.getMemberDetail().getTelPhone()).isEqualTo("01031984329");

        assertThat(savedMember.getLoginId()).isEqualTo(savedMember.getMemberDetail().getLoginId());


    }
}
