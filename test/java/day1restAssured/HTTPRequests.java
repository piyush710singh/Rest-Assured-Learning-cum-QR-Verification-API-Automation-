package day1restAssured;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import java.util.HashMap;


public class HTTPRequests {
    String id;

    //It follows BDD style (gherkin language)
    //get post put delete
    //given() : (content type, set cookies, add auth, add param, set headers info etc,
    // when() : get, post, put, delete
    // then() : validate status codes, extract responses, extract headers cookies and responses

//testing get users : https://reqres.in/api/users/2

    //post create
    //put
@Test(priority = 1)
    void getUsersList(){
    given()
            //get request doesnt have any
            .header("x-api-key", "reqres_cc4b3a01bc6b4ae8953044bd1f1e43e3")
            .when()
            .get("https://reqres.in/api/users?page=2")


            .then()
            .statusCode(200)
            .body("page", equalTo(2))
            .log().all();
}

@Test (priority = 2)
void createUser(){

    HashMap data = new HashMap<>();
    data.put("email", "eve.holt@reqres.in");
    data.put("password", "pistol");


    id =given()
            .header("x-api-key", "reqres_cc4b3a01bc6b4ae8953044bd1f1e43e3")
            .contentType("application/json")
            .body(data)
            .when()
            .post("https://reqres.in/api/users")
            .then()
            .statusCode(201)
            .extract()
            .path("id");



    //to capture the id for api chaining and perform further operations like update

    // now we will upadate the api using id (api chaining)





}


@Test(priority = 3, dependsOnMethods = {"createUser"})
    void updateUser() {
    HashMap data = new HashMap<>();
    data.put("name", "Piyush");
    data.put("job", "automation");
//    data.put("email", "eve.holt@reqres.in");
//    data.put("password", "pistol");


      given()
            .header("x-api-key", "reqres_cc4b3a01bc6b4ae8953044bd1f1e43e3")
            .contentType("application/json")
            .body(data)

            .when()

            .put("https://reqres.in/api/users/"+id)
            .then()

            .statusCode(200)
            .log().all();



}


//post request involves a body
    //we can create body in many ways
    //ways we can create the body
    //Hashmap,
    //org.json
    //POJO class (Plain old java object most famous way)
    //External json file


}
