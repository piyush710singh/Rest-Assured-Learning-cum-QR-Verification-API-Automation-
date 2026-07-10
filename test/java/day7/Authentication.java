package day7;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class Authentication {

    //authentication - user is valid or not
    //authorization -- user is valid but has the required access and permissions or not

    //Basic, Digest, Preemptive, Bearer Token, OAuth1.0, OAuth2.0, API Key

    //username and password
    @Test
    void testBasicAuthentication(){
        given()
                .auth().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();


    }

    @Test
// process internally check the username and password
    void testDigestAuthentication(){
        given()
                .auth().digest("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();


    }

    //Combination of Basic and Digest

    void testPreemptiveAuthentication(){
        given()
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();


    }

    //Bearer token = we have to create our token

    @Test
    void testBearerTokenAuthentication(){

       String btoken = "ABZ7mU3jy3mPT7debA2z17+DOJgjmd8PeCWURvA7HnNBRvyv69QQDtmXmQWqycAJi5TWqSQ57RWuoTjzkPldmWeSkbMmn0fLjzze3W7g06wtM33eVIWklhQOPuRUS/sMuTBxRjNzIrNQcrNHG3VCZTzAZQ34+Pn4vTNCN873FrVSiF/gxPaoCF26jljNE39dhec9heoSTQZP65hhSlfRQx0YovuNqkDCb6xKf0Vo2oQIAfZ5FGM+cmIIF2iig/Hl4AW0woR15v2EVhf0TfSLSBHjB3AER6bTs+AUS96+Ido+vmt4nxpVnpxImZKqsmvAH3bCkS/IHHmH7TdfsJWDdgE9Xub8Z9yStB5elml1TKIB8QdI2X0Nm/sV5EHUIBbH9P+VLDKkyVoOkHFtYTrhv1sS0HEdb9w8FPiDt7O/gzLLTbKH4kT/65qDms8KtzS27l7G+hiAxI/elnkTrT2cHh3xdAAvooAY/xNDQpDULQzpA+p4gfdfH5cd9ZweXeWSLf5JsJ7cFVpIbHDTSnmznTG/AjEdwhFYegelZ4F08or/v+9aUnM2oBVAOinDPjlkQllZTlgU84F5ndEibq/+T6v+BWQc9ZkhLwhbz0tPHHY33tvL6wwNi9swonaeQhxdKu2evnEGreo1FxLKVYvmxXtJwmwDFIdDd+sUD8hUqIhSfG42GcjKgvfVOYBFUUw1cI0oMpc0Qcp2DJGjC2rSPvtDtliVcNyKFMk69QanZD7pjVjFQUl3Yrcr2BA6V5jrhplxg6gUoBjt11jitMGdYzrbh9LStuzcPEkCgQvy+hEvejXI5FZDJrLzTXxo45QCQCP2iDbTbsEPYRDc4ClW0mT2kKVSMriCvNeNGQ+t9p3KS1KX6q/GNDtPesoe0QldMUoEq8jSj8b+ozi0hqKIS9gUZTx6fb7xbWyhLvGDDHS9WQVevIHaOznINgYBD+GG2GtXM1yZbnS2/OGAROR7CzQ=.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc4MTg2NDMzOCwianRpIjoiYjY3NWE0NjItZTZmZS00YjVmLTkyZWMtNmVhMjlhNDJmMjQ0IiwidHlwZSI6ImFjY2VzcyIsInN1YiI6IjE5IiwibmJmIjoxNzgxODY0MzM4LCJjc3JmIjoiMDdkYTNjMGEtZWJjZi00OWFmLTkyMTctMjEyZWJkZDg5N2QwIiwiZXhwIjoxNzgxOTUwNzM4fQ.R0H5AsO0_d65fwcqk7ByViLHHjlpoqZ6LmhlVip0F7U";
        given()
                .headers("token", btoken)
        .when()
                .post("https://facefinder-uat.meon.co.in/backend/initiate_request")
                .then()
                .statusCode(200);



    }


    //for classic oauth1 authentication we need consumerkey, consumersecret, accesstoken, tokensecret
    void testOAuth1Authentication(){

        given()
                .auth().oauth("", "", "", "")
                .when()
                .get("url")
                .then()
                .statusCode(200);
    }



    //just oauth2 token need to be added
    void testOAuth2(){

        //here we need only access token of another server like google/github


    }

//    void testAPIKeyAuthentication(){
//        given()
//
//
//    }

    //to create random dummy data we use faker library in rest assured
    // Faker Library can create random dummy data easily (Faker API)
    //Another dependency is needed to integrate the same
    //Java faker
    //

}
