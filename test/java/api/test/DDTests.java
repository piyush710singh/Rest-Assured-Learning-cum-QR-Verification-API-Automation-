package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DDTests {


    //Create many users and then delete them all

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String UserID, String UserName, String firstName, String LastName, String email, String Password, String Phone ){

        User userpayload = new User();

        userpayload.setId(Integer.parseInt(UserID));
        userpayload.setUsername(UserName);
        userpayload.setFirstName(firstName);
        userpayload.setLastName(LastName);
        userpayload.setPhone(Phone);
        userpayload.setEmail(email);
        userpayload.setPassword(Password);


        Response response = UserEndpoints.createUser(userpayload);
        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteuser(String UserName){


        Response res = UserEndpoints.deleteUser(UserName);
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    //ExtentReport Utility file and testng.xml file is needed to get the reports
}
