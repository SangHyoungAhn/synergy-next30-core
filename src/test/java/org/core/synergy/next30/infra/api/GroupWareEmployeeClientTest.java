package org.core.synergy.next30.infra.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.core.synergy.next30.sync.infra.api.GroupWareEmployeeClient;
import org.core.synergy.next30.sync.infra.api.dto.employee.EmployeeData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "gw.base-url=https://gw.dongamedia.com",
        "gw.hash-key=79007189296633872192398228345530954072110613",
        "gw.group-seq=gcmsAmaranth29108"
})
public class GroupWareEmployeeClientTest {

    @Autowired
    private GroupWareEmployeeClient employeeClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("그룹웨어 사원 목록 실데이터 수신 테스트")
    void fetchEmployeeRealTest() throws JsonProcessingException {
        // given
        String callerName = "API_gcmsAmaranth29108";
        String accessToken = "mwABnLttSwfcVUrhQJvRTvsQOpU069";
        String hashKey = "79007189296633872192398228345530954072110613";

        // when
        List<EmployeeData> employees = employeeClient.fetchEmployees(accessToken);

        // then
        assertThat(employees).isNotNull();

        System.out.println("======= [API 수신 데이터 확인] =======");
        System.out.println("전체 사원 수: " + employees.size());

        if (!employees.isEmpty()) {
            // 첫 번째 사원의 데이터를 JSON으로 예쁘게 출력해서 필드 매핑 확인
            String json = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(employees.get(0));
            System.out.println("샘플 데이터:\n" + json);
        }
        System.out.println("====================================");
    }
}