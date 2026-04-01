package org.core.synergy.next30.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.core.synergy.next30.global.common.BaseTimeEntity;

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
    public MemberDetail(Member member, String email, String telPhone, String profileImageUrl,
                        String empMbti, String empAsset, String empFocus, String empKeywords) {
        this.member = member;
        this.email = email;
        this.telPhone = telPhone;
        this.profileImageUrl = profileImageUrl;
        this.empMbti = empMbti;
        this.empAsset = empAsset;
        this.empFocus = empFocus;
        this.empKeywords = empKeywords;
    }

    // 연관관계 편의 메서드 (Member에서 호출용)
    public void assignMember(Member member) {
        this.member = member;
    }
}
