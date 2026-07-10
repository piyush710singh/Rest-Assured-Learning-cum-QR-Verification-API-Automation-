package RestAssuredDay4;

import io.restassured.http.ContentType;
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


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class ParsingJSONResponseData {



    @Test
    void testJsonResponse(){

//        //Approach 1
//
//        given()
//                .contentType("application/json")
//                .when()
//                .get("http://localhost:8081/store")
//                .then()
//                .statusCode(200)
//
//                .body("book[0].author", equalTo("George Orwell"));
//


        //Approach 2

        Response res = given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8081/store");

                //validating the responses
//        Assert.assertEquals(res.getStatusCode(), 200);
//        Assert.assertEquals(res.getHeader("Content-Type"), "application/json" );
//        String booktitle = res.jsonPath().get("book[3].author").toString();
//        Assert.assertEquals(booktitle, "George Orwell");


        //Now searching a particular value from the response body


        //all the json response is one json object
        //search for book title
        JSONObject jo = new JSONObject(res.asString());//converting response to json object type
        for(int i=0; i<jo.getJSONArray("book").length(); i++) {
            String bookauthor = jo.getJSONArray("book").getJSONObject(i).get("author").toString();
            boolean status = false;
            if (bookauthor.equals("Lord of the Rings")) {
                status = true;
                break;

            }
            Assert.assertEquals(status, true);
        }

        //Validate total of price with an expected value
        double totalprice =0;
        JSONObject jp = new JSONObject(res.asString());
        for(int j=0; j<jp.getJSONArray("book").length();j++){
            String price = jp.getJSONArray("book").getJSONObject(j).get("price").toString();
            totalprice = totalprice +Double.parseDouble(price);
        }
        Assert.assertEquals(totalprice, 100);

    }

/*//
//

RESPONSE >>>> PARSE IT INTO JSON OBJECT (CLASS)
>>>json ARRAY >>>> NO OF JSON OBECTS
>> EACH JSON OBJECT >>> HAS ALL THE KEY AND VALUES WE CAN RETRIEVE


//            m*/

}


//we can also access using jsonarray Jsonarray jarr = new Jsonarray(res.asString())>>> jrr.getJSONobj(0).getjsonarray("course