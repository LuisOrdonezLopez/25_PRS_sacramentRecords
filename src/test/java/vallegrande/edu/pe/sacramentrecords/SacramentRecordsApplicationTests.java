package vallegrande.edu.pe.sacramentrecords;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.r2dbc.url=r2dbc:postgresql://localhost:5432/testdb",
        "spring.r2dbc.username=test",
        "spring.r2dbc.password=test",
        "eureka.client.enabled=false",
        "spring.cloud.discovery.enabled=false",
        "spring.cloud.service-registry.auto-registration.enabled=false"
})
class SacramentRecordsApplicationTests {

    @Test
    void contextLoads() {
    }

}
