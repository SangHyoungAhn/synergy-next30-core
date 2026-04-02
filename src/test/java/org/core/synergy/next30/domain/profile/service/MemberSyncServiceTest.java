package org.core.synergy.next30.domain.profile.service;

import org.assertj.core.api.Assertions;
import org.core.synergy.next30.domain.profile.entity.Member;
import org.core.synergy.next30.domain.profile.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberSyncServiceTest {

    @Autowired
    private MemberSyncService memberSyncService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("그룹웨어 API로부터 사원 정보를 가져와 DB 동기화 및 데이터 정제를 검증한다.")
    @Rollback(false)
    void syncAndVerifyTest () {
        // given

        // when
        memberSyncService.syncGroupWareMembers();

        // then
        //전체카운트 확인
        long count = memberRepository.count();
        System.out.println("저장된 총 사원 수: " + count);
        assertThat(count).isGreaterThan(0);

        String testLoginId = "shahn0718";
        Optional<Member> memberOpt = memberRepository.findById(testLoginId);

        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            String email = member.getMemberDetail().getEmail();
            String tel = member.getMemberDetail().getTelPhone();

            System.out.println("✅ 가공된 이메일: " + email);
            System.out.println("✅ 가공된 전화번호: " + tel);

            // 검증: 이메일에 @donga.com이 붙어 있고, 전화번호에 하이픈이 없는지
            assertThat(email).contains("@donga.com");

            if (tel != null) {
                assertThat(tel).doesNotContain("-");
            }
        }
    }
}
