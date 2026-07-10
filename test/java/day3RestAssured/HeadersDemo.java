package day3RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import java.util.HashMap;



//since headers are constant
public class HeadersDemo {

    @Test
    void testHeaders(){
        given()
                .when()
                .get("http://www.google.com/")
                .then()
                .header("content-type", "\t\n" +
                        "text/html; charset=ISO-8859-1")
                .header("server", "gws");


    }


    @Test
    void getHeaders() {

        Response res = given()
                .when()
                .get("http://www.google.com/");

        //get single header info
//
//        System.out.println(res.getHeader("server"));
//        System.out.println(res.getHeader("content-typec"));
//    }

        Headers headers = res.getHeaders();//name and values
        for(Header h : headers){
            System.out.println(h.getName() + "       "+ h.getValue());
        }



        //log().all includes status code, body, cookie, headers everything
        //log.body (response body
        //log.cookies (only cookies
        //log.headers (only headers
    }

}
