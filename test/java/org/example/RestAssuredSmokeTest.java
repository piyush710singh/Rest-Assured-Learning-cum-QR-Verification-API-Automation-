package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredSmokeTest {

    @Test
    public void parsesJsonWithoutHittingNetwork() {
        JsonPath jsonPath = new JsonPath("{\"name\":\"rest-assured\",\"enabled\":true}");

        Assert.assertEquals(jsonPath.getString("name"), "rest-assured");
        Assert.assertTrue(jsonPath.getBoolean("enabled"));
    }
}
