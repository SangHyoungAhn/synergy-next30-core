package org.core.synergy.next30.domain.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.core.synergy.next30.global.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @Column(name="login_id")
    private String loginId; // 로그인 ID (PK)

    @Column(nullable = false)
    private String empCd;

    @Column(nullable = false)
    private String korNm;

    //회사코드 부서 직책 직급
    private String coCd;

    private String deptCd;
    private String deptNm;

    //직급코드, 직급명
    private String hclsNm;
    private String hclsCd;

    //직책코드, 직책명
    private String hrspCd;
    private String hrspNm;

    private String enrlFg;


    @OneToOne(mappedBy="member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberDetail memberDetail;

    public void assignDetail(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
        // 무한 루프 방지 및 양방향 세팅
        if (memberDetail != null && memberDetail.getMember() != this) {
            memberDetail.assignMember(this);
        }
    }

}
