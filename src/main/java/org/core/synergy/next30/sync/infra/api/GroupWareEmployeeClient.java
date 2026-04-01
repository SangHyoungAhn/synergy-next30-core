package org.core.synergy.next30.sync.infra.api;


import lombok.RequiredArgsConstructor;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeData;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeListRequest;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeListResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GroupWareEmployeeClient {

    private final GroupWareApiClient apiClient;

    public List<EmployeeData> fetchEmployees(String token){
        String url = "/apiproxy/api16S05";

        EmployeeListRequest request = EmployeeListRequest.builder()
                .header(EmployeeListRequest.RequestHeader.builder()
                        .groupSeq("gcmsAmaranth29108")
                        .build())
                .body(EmployeeListRequest.RequestBody.builder()
                        .coCd("1400")
                        .enrlFg("J01")
                        .rmkDc("1")
                        .build())
                .build();

        EmployeeListResponse response = apiClient.execute(url, token, request, EmployeeListResponse.class);

        System.out.println("DEBUG - Raw Response: " + response);

        return (response != null && response.getResultData() != null)
                ? response.getResultData()
                : Collections.emptyList();

    }

}
