package org.core.synergy.next30.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.core.synergy.next30.global.common.BaseTimeEntity;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeData;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends BaseTimeEntity {

    @Id
    private String loginId; // 숫자가 아닌 Member의 loginId를 PK로 사용.

    @MapsId // Member 엔티티의 loginId 값을 이 엔티티의 ID(PK)로 매핑.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id") // DB 컬럼명을 login_id로 설정 (Member의 loginId와 연결)
    private Member member;

    private String email;
    private String telPhone;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    private String empMbti;

    @Column(columnDefinition = "TEXT")
    private String empAsset;

    @Column(columnDefinition = "TEXT")
    private String empFocus;

    private String empKeywords;

    @Builder
    public MemberDetail(Member member, String beforeGeneratedTel, String profileImageUrl,
                        String empMbti, String empAsset, String empFocus, String empKeywords) {
        this.member = member;
        // 이메일 결합
        if(member != null){
            this.loginId = member.getLoginId();
            this.email = member.getLoginId() + "@donga.com";
        }
        // 전화번호 정제
        this.telPhone = (beforeGeneratedTel != null) ? beforeGeneratedTel.replaceAll("[^0-9]","") : null;

        this.profileImageUrl = profileImageUrl;
        this.empMbti = empMbti;
        this.empAsset = empAsset;
        this.empFocus = empFocus;
        this.empKeywords = empKeywords;
    }

    // 연관관계 편의 메서드 (Member에서 호출용)
    public void assignMember(Member member, String beforeGeneratedTel) {
        this.member = member;
        this.loginId = member.getLoginId();
        this.email = member.getLoginId() + "@donga.com";
        this.telPhone = (beforeGeneratedTel != null) ? beforeGeneratedTel.replaceAll("[^0-9]","") : null;
    }

    public void updateFromApi(EmployeeData data) {
        // 1. 전화번호 가공 (하이픈 제거)
        if (data.getMobileTel() != null) {
            this.telPhone = data.getMobileTel().replaceAll("-", "");
        }
        // 2. 이메일 가공 (이미 도메인이 있으면 그대로, 없으면 @donga.com 붙이기)
        if (data.getEmailAddr() != null) {
            String email = data.getEmailAddr();
            this.email = email.contains("@") ? email : email + "@donga.com";
        }
        // 3. 기타 필드 업데이트 (필요한 경우)
        this.profileImageUrl = data.getProfileImageUrl();
    }
}
