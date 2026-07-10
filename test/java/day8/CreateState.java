package day8;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class CreateState {

    @Test

    void test_createState(ITestContext context){

        JSONObject data = new JSONObject();
        data.put("company_name", "piyushyZqd");
        data.put("secret_token", "P110XCvuWh3RRG7RNSb0NbNmDR6WHc7G");
        String bearertoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc4MjMwNDI3NCwianRpIjoiZGE5NGM3NzAtNGFkMS00MjBhLWIzZTItZWE3NGVkZTcwNGYzIiwidHlwZSI6ImFjY2VzcyIsInN1YiI6Im1hbmRvdHNlY3VyaXRpZXMiLCJuYmYiOjE3ODIzMDQyNzQsImNzcmYiOiI5MWU2NTRjYS1kNDVjLTQ2NmQtODA2ZS1kZjExODk5MGQ0YWIiLCJleHAiOjE3ODIzMDc4NzQsInVzZXIiOiJwaXl1c2hAbWVvbi5jby5pbiIsImFkbWluX3VzZXIiOiJtYW5kb3RzZWN1cml0aWVzXzc2QG1lb24uY28uaW4ifQ.3_2KYbb5jG63kP7PkinQ4WhvHZcxeGFDAMW2G7TO1OE";
        Response res =  given()
                .header("Authorization", "Bearer"+bearertoken)
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("https://digilocker.meon.co.in/get_access_token");



        String state = res.jsonPath().getString("state");
        String clientToken = res.jsonPath().getString("client_token");

        System.out.println(state);
        System.out.println(clientToken);
        context.setAttribute("c_token", clientToken);




    };


}
