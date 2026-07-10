package RestAssuredDay2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostBodyTypes {
    private static final String BASE_URL = "http://localhost:8081";
    int id;
//
//    @Test
    void testPostusingHashmap() {
        HashMap data = new HashMap<>();
        data.put("title", "Scott");
        data.put("description","scotland is a place");
        data.put("completed", true);

        id = given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(data)
        .when()
                .post("/api/items")
        .then()
                .statusCode(201)
                .body("title", equalTo("Scott"))
                .body("description", equalTo("scotland is a place"))
                .body("completed", equalTo(true))
                .header("content-Type", "application/json")
                .log().all()
                .extract()
                .path("id");

    }

    //given() : (content type,request body,  set cookies, add auth, add param, set headers info etc,
    // when() : get, post, put, delete
    // then() : validate status codes, extract responses, extract headers cookies and responses


    //deleting a record
//    @Test

    void testDelete(){
        given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .when()
                .delete(BASE_URL+"/3")

                .then()
                .statusCode(200);
    }

//    @Test (dependsOnMethods = {"testPostusingHashmap"})
    void getUsersList(){
        given()
                //get request doesnt have any

                .when()
                .get(BASE_URL + "/health")


                .then()
                .statusCode(200)
                .log().all();
    }





    //org.json method to post a request


//    @Test
    void testPostUsingJSonLibrary(){

        JSONObject data = new JSONObject();
        data.put("title", "Scottish malt");
        data.put("description","scotland is a beer place");
        data.put("completed", false);

        given()
                .baseUri(BASE_URL)
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("/api/items")
                .then()
                .statusCode(201)
                .body("title", equalTo("Scottish malt"))
                .body("description", equalTo("scotland is a beer place"))
                .body("completed", equalTo(false))
                .log().all();

    }


    //Using a POJO class


//    @Test
    void testUsinfPOJO(){

        pojo_postrequest pp = new pojo_postrequest();
        pp.setTitle("Irish");
        pp.setDescription("peaky blinders whisky");
        pp.setCompleted(false);

        given()  .baseUri(BASE_URL)
                .contentType("application/json")
                .body(pp)
                .when()
                .post("/api/items")
                .then()
                .statusCode(201)
                .body("title", equalTo("Irish"))
                .body("description", equalTo("peaky blinders whisky"))
                .body("completed", equalTo(false))
                .log().all();
    }


    // Post request using external JSOn
    //file
    @Test
    void testPostusingExternalJSONFile() throws FileNotFoundException {

        File f = new File("C:\\Users\\piyus\\IdeaProjects\\RestAssuredLearning\\src\\main\\resources\\body.json");
        FileReader fr = new FileReader(f);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);
        given()  .baseUri(BASE_URL)
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("/api/items")
                .then()
                .statusCode(201)
                .body("title", equalTo("My item"))
                .body("description", equalTo("Optional text"))
                .body("completed", equalTo(false))
                .log().all();



    }


    //path and query parameters
    //after question mark = query parameter
    // Serialization pojo -----json
    //Deserialization json------pojo
    // JacksonBinder =Library to bind the json to POJO
    //


}
