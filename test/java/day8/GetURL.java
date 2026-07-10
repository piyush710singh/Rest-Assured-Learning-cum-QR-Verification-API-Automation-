package day8;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetURL {


    @Test(dependsOnMethods = "test_createState")
    void test_getUser(ITestContext context){
        String clientToken = (String) context.getAttribute("c_token").toString();
        JSONObject data = new JSONObject();
        data.put("client_token", clientToken);
        data.put("redirect_url","https://digilocker.meon.co.in/digilocker/thank-you-page" );
        data.put("company_name", "piyushyZqd");
        data.put("documents","aadhaar,pan");

        Response res = given()
                .body(data.toString())
                .contentType("application/json")
                .when()
                .post("https://digilocker.meon.co.in/digi_url");

        System.out.println(res.jsonPath().getString("url"));


    }

    //framework -- maintain all project related files
    // used in reusability, maintainablity, readibility
    //Hybrid driven (combination of different type)
    //phases of designing framework
    //Requirement (functional static, swagger from dev)>> automation tool rest assured lib
    //Design folders, reports, etc
    //Developmeent and execution of the framework
    // Routes or properties file to contain all the urls of the project in a file
    // Endpoints contain pre-requisites and request types
    // Test cases contains validations of the endpoints then()
    // Payloads are POJO,JSON, Hashmaps
    // Utilities : DataProviders, Extent reports, XL Utility
    // Test Data : Userdata, Storedata, Petdata
    // CI/CD : Git, Git Hub, Jenkins
    // log4j for logging of erros
}

