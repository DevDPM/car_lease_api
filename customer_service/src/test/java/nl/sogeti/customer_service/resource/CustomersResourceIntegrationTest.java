package nl.sogeti.customer_service.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.smallrye.jwt.build.Jwt;
import nl.sogeti.customer_service.dto.CustomerDetail;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
@TestProfile(CustomersResourceIntegrationTest.BuildTimeValueChangeTestProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomersResourceIntegrationTest {

    public static class BuildTimeValueChangeTestProfile implements QuarkusTestProfile {
        @Override
        public Map<String, String> getConfigOverrides() {
            return Map.of("quarkus.hibernate-orm.persistence-xml.ignore", "true",
                      "smallrye.jwt.sign.key.location", "privateKey.pem",
                      "quarkus.http.test-port", "9876",
                      "quarkus.smallrye-jwt.enabled","true");
        }

        @Override
        public String getConfigProfile() {
            return QuarkusTestProfile.super.getConfigProfile();
        }
    }

    private String JWT_TOKEN;

    @BeforeEach
    void init() {
        JWT_TOKEN = Jwt.issuer("http://localhost:9876/")
                        .upn("daniel@example.com")
                        .groups(new HashSet<>(List.of("Fun-User")))
                        .sign();
    }

    @Test
    @Order(1)
    void get() {
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .pathParam("id", 1)
                .get("/customers/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Daniel"))
                .extract()
                .body()
                .jsonPath()
                .prettyPrint();
    }

    @Test
    @Order(2)
    void create_created() {
        // given
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setName("Wesley");

        // when
        String location = given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .body(customerDetail)
                .post("/customers")
                .then()
                .statusCode(201)
                .extract().header("Location");

        // then
        assertEquals(location, "http://localhost:9876/customers/2");
    }

    @Test
    @Order(3)
    void update() {
        // given
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setName("Wesley");

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .body(customerDetail)
                .pathParam("id", 1)
                .put("/customers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(4)
    void getAll() {
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .get("/customers")
                .then()
                .body("customers.size()", equalTo(2))
                .body("customers[0].name", equalTo("Wesley"))
                .body("customers[1].name", equalTo("Wesley"));
    }

    @Test
    @Order(5)
    void delete_customer_deleted() {
        // given
        final int ID = 1;

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .pathParam("id", ID)
                .delete("/customers/{id}")
                .then()
                .statusCode(204);

    }

    @Test
    @Order(6)
    void delete_customer_not_found() {
        // given
        final int ID = 999;

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .pathParam("id", ID)
                .delete("/customers/{id}")
                .then()
                .statusCode(404);

    }
}