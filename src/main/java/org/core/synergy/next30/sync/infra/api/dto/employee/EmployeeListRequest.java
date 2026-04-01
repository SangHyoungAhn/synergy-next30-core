package org.core.synergy.next30.sync.infra.api.dto.employee;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeListRequest {

    private RequestHeader header;
    private RequestBody body;

    @Getter
    @Builder
    public static class RequestHeader {
        private String groupSeq; //(필수값) 그룹시퀀스 gcmsAmaranth29108
        private String callerName;
        private int empSeq;
        private String tId;
        private String pId;

    }

    @Getter
    @Builder
    public static class RequestBody{
        private String coCd; // 회사코드
        private String enrlFg; //재직구분: J01 - 재직, J02 - 파견, J03 - 휴직
        private String rmkDc; // 0:일용직 1:상용직

        @Builder.Default
        private String outputType = "list";
    }
}
