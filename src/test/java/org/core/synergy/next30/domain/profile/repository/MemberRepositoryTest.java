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
                .empCd("C019005")
                .korNm("안상형")
                .coCd("1400")
                .deptCd("C101225")
                .deptNm("비즈테크팀")
                .hrspNm("대리")
                .hrspCd("EC3")
                .hclsNm("-")
                .hclsCd("C10")
                .build();

        MemberDetail detail = MemberDetail.builder()
                .member(member)
                .empMbti("INTJ")
                .empAsset("JAVA SPRING")
                .build();
        // when
        member.assignDetail(detail);
        memberRepository.save(member);

        em.flush();
        em.clear();
        // then
        Member savedMember = memberRepository.findById("shahn0718")
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));


        assertThat(savedMember.getKorNm()).isEqualTo("안상형");
        assertThat(savedMember.getDeptNm()).isEqualTo("비즈테크팀");

        assertThat(savedMember.getMemberDetail()).isNotNull();
        assertThat(savedMember.getMemberDetail().getEmpMbti()).isEqualTo("INTJ");
        assertThat(savedMember.getMemberDetail().getTelPhone()).isEqualTo("01031984329");

        assertThat(savedMember.getLoginId()).isEqualTo(savedMember.getMemberDetail().getLoginId());


    }
}
