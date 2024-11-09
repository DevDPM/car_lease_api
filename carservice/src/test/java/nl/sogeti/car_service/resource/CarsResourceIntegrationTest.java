package nl.sogeti.carservice.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import nl.sogeti.carservice.dto.CarDetails;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarsResourceIntegrationTest {

    private String JWT_TOKEN;

    @BeforeEach
    void setUp() {
        JWT_TOKEN = Jwt.issuer("http://localhost:9877/")
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
                .get("/cars/{id}")
                .then()
                .statusCode(200)
                .body("model", equalTo("RS"))
                .extract()
                .body()
                .jsonPath()
                .prettyPrint();
    }

    @Test
    @Order(2)
    void create_created() {
        // given
        CarDetails carDetails = new CarDetails();
        carDetails.setVersion("v8.0");

        // when
        String location = given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .body(carDetails)
                .post("/cars")
                .then()
                .statusCode(201)
                .extract().header("Location");

        // then
        assertEquals("http://localhost:9877/cars/2", location);
    }

    @Test
    @Order(3)
    void update() {
        // given
        final int ID = 2;
        CarDetails carDetails = new CarDetails();
        carDetails.setVersion("v12.0");

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .body(carDetails)
                .pathParam("id", ID)
                .put("/cars/{id}")
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
                .get("/cars")
                .then()
                .body("cars.size()", equalTo(2))
                .body("cars[0].version", equalTo("v4.1"))
                .body("cars[1].version", equalTo("v12.0"));
    }

    @Test
    @Order(5)
    void delete_car_deleted() {
        // given
        final int ID = 1;

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .pathParam("id", ID)
                .delete("/cars/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void delete_car_not_found() {
        // given
        final int ID = 999;

        // when
        given()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + JWT_TOKEN)
                .when()
                .pathParam("id", ID)
                .delete("/cars/{id}")
                .then()
                .statusCode(404);
    }
}