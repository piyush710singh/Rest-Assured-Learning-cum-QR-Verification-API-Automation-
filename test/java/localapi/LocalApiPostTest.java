package localapi;

import io.restassured.http.ContentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LocalApiPostTest {
    private final LocalApiServer server = new LocalApiServer();

    @BeforeClass
    public void setUp() throws IOException {
        server.start();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        server.stop();
    }

    @Test
    public void postUserCreatesLocalResource() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Piyush");
        payload.put("job", "automation");
        payload.put("email", "piyush@example.com");

        given()
                .baseUri(server.baseUrl())
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .body("name", equalTo("Piyush"))
                .body("job", equalTo("automation"))
                .body("email", equalTo("piyush@example.com"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }


    //Body using Org.json



}
