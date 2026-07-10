package Day6;

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


public class JSONSchemaValidation {


    @Test
    void jsonschemaValidation(){
        given()
                .when()
                .get("https://kycuat.meon.co.in/get_user_data_new")
                .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemavalidation.json"));


    }

    @Test
    //we dont have xmlschema validator class like the json schema

    void XMLSchemaValidation(){
        given()
                .when()
                .get("https://ipo.meon.co.in/bharatbhushan/sitemap")
                .then()
                .assertThat().body(RestAssuredMatchers.matchesXsd(new File("C:\\Users\\piyus\\IdeaProjects\\RestAssuredLearning\\src\\test\\java\\Day6\\xmlvalidation.xsd")));


    }

    //Serialization and DeSerialization
    //Body with post request >>json >>>Response (json)



}
