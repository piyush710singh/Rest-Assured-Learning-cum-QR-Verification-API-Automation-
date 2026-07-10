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
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class ParsingXMLResponse {


    @Test
    void testXMLResponse(){

        //Approach1

//        given()
//
//                .when()
//                .get("https://ipo.meon.co.in/bharatbhushan/sitemap")
//                .then()
//                .statusCode(200)
//                .header("Content-Type", "application/xml; charset=utf-8")
//                .body("urlset.url[0].lastmod", equalTo("2026-06-17"));
//




        //Now using approach 2 using xml parser

        Response res =
                given()
                        .when()
                        .get("https://ipo.meon.co.in/bharatbhushan/sitemap");

//        Assert.assertEquals(res.getStatusCode(), 200);
//        Assert.assertEquals(res.header("Content-Type"), "application/xml; charset=utf-8");
//        String frequency = res.xmlPath().get("urlset.url[0].changefreq").toString();
//        Assert.assertEquals(frequency, "daily");


        //Approach 3 using XMLPath Class

        XmlPath xmlobj = new XmlPath(res.asString());

        List<String> frequency = xmlobj.getList("urlset.url.changefreq");
//        frequency.get(0);

        boolean status = false;

        for( String changefreq : frequency){
            System.out.println(changefreq);

            if(changefreq.equals("monthly")){
                status = true;
                        break;
            }
        }

        Assert.assertEquals(status, true
        );

        // Automate File Upload and Download




    }
}
