package org.core.synergy.next30.domain.profile.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.core.synergy.next30.domain.profile.entity.Member;
import org.core.synergy.next30.domain.profile.entity.MemberDetail;
import org.core.synergy.next30.domain.profile.repository.MemberRepository;
import org.core.synergy.next30.sync.infra.api.GroupWareAuthService;
import org.core.synergy.next30.sync.infra.api.GroupWareEmployeeClient;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSyncService {

    private final MemberRepository memberRepository;
    private final GroupWareEmployeeClient groupWareEmployeeClient;

    @PersistenceContext
    private EntityManager em;

    @Value("${gw.access-token}")
    private String accessToken;

    @Transactional
    public void syncGroupWareMembers(){

        List<EmployeeData> apiEmployees = groupWareEmployeeClient.fetchEmployees(accessToken);

        if(apiEmployees.isEmpty() || apiEmployees == null){
            log.warn("수신된 사원 데이터가 없습니다");
            return;
        }

        for (EmployeeData data : apiEmployees) {
            String loginId = data.getLoginId();
            if (loginId == null) continue;

            Optional<Member> optionalMember = memberRepository.findById(loginId);

            if (optionalMember.isEmpty()) {
                // 1. DB에 없는 "신규 사원"일 경우
                Member newMember = Member.builder()
                        .loginId(loginId)
                        .empCd(data.getEmpCd()) // 필수값
                        .korNm(data.getKorNm()) // 필수값
                        .build();

                MemberDetail detail = MemberDetail.builder().member(newMember).build();
                newMember.assignDetail(detail);

                // 값 업데이트
                newMember.updateInfo(data);
                detail.updateFromApi(data);

                // [가장 중요] save() 대신 persist()를 사용하여 강제 INSERT!
                em.persist(newMember);
            } else {
                // 2. DB에 이미 있는 "기존 사원"일 경우 (Dirty Checking으로 알아서 UPDATE 됨)
                Member member = optionalMember.get();
                member.updateInfo(data);

                if (member.getMemberDetail() == null) {
                    MemberDetail detail = MemberDetail.builder().member(member).build();
                    member.assignDetail(detail);
                    em.persist(detail); // 디테일만 신규 생성일 경우
                }
                member.getMemberDetail().updateFromApi(data);
            }
        }
        log.info("성공: {}명의 사원 정보를 동기화했습니다.", apiEmployees.size());
    }
}
