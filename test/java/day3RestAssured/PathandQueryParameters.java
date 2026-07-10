package day3RestAssured;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import java.util.HashMap;


public class PathandQueryParameters {

    @Test
    void testQueryAndPathParameters(){
        given()
                .header("x-api-key", "reqres_cc4b3a01bc6b4ae8953044bd1f1e43e3")
                .pathParam("mypath", "users")
                .queryParam("page", 2)
                .queryParam("id", 5)


                .when()
                .get("https://reqres.in/api/{mypath}")
                .then()
                .statusCode(200)
                .log().all();

    }



    //cookies and headers as part of the response

}
