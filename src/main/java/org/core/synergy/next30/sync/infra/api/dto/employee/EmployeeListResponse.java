package org.core.synergy.next30.sync.infra.api.dto.employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class EmployeeListResponse {

    private int resultCode;
    private String resultMsg;
    private List<EmployeeData> resultData;
}
