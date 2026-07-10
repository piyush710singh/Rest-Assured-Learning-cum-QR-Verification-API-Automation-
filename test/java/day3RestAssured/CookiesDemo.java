package day3RestAssured;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

//Cookies are found in the response body

public class CookiesDemo {


    @Test
    void testCookies(){

        given()

                .when()
                .get("http://www.google.com/")

                .then()
                .cookie("NID", "532%3DRDNHMfnouo090OzBQtFiavT5u_e9x8KNvnerXGZus-nrpXpTVbGh5lH-Fu84bh5nYM6mMmDWcObu9KuPcBJckf4WbLW3Xk2K1BQfbPGoSAkjHLGHA1Ey6yg8ukwRqxAR5LUWBn6ls4zOtWKE-DLIqoL2Of6CLvJUz4zPs0DVpU-ltZQPgoegRTC_YZ1tZTnCDYWD2AkeZvv3E_uwzRyoSJ0w")
                .log().all();



    }

    @Test

    void getCookiesInfo(){

        Response res = given()
                .when()
                .get("http://www.google.com/");


        //to get one cookie

//        String cookie_value = res.getCookie("NID");
//        System.out.println(cookie_value);

        // to get all cookies

        Map<String, String> cookies_values = res.getCookies();
        //System.out.println((cookies_values.keySet());

        for(String k :cookies_values.keySet()){
            System.out.println(k +"     "+ res.getCookie(k));
        }


    }


    //Validating Response headers as the headers are always constant

    

}
