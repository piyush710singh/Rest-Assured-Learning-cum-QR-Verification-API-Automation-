package Day5;
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

public class FileUploadDownloadDemo {

    @Test
    void singleFileUpload(){
        File myfile = new File("C:\\Users\\piyus\\IdeaProjects\\RestAssuredLearning\\target\\WIN_20260617_13_05_17_Pro.jpg");

        given()
                .multiPart("file", myfile)
                .when()
                        .post("https://facefinder-webview.meon.co.in:444/backend/save_image/676507c9ec95493d8f0a4863a00c18ba")
                .then()
                .statusCode(200);
    }


    @Test

    void fileDownload(){

    }


    //Schema is for data type validation and response is for data validation


}
