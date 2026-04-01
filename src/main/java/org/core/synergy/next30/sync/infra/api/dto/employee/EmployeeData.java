package org.core.synergy.next30.sync.infra.api.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeData {

    private String empCd;      // 사원코드
    private String korNm;      // 성명(한글)
    private String loginId;    // 로그인ID

    private String deptCd;     // 부서코드
    private String deptNm;     // 부서명

    private String hclsCd;     // 직급코드
    private String hclsNm;     // 직급명

    private String hrspCd;     // 직책코드
    private String hrspNm;     // 직책명

    private String emailAddr;  // 이메일주소
    private String mobileTel;  // 휴대전화번호
    private String telNum;     // 전화번호(내선)

    private String enrlFg;     // 재직구분 (J01:재직 등)
    private String rmkDc;      // 고용구분
}
