package api.test;

import api.endpoints.UserEndpoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTest {

    Faker faker;
    User userPayload;
    public Logger logger; // Log4j2 logger
    //CRUD check of user we need here

    @BeforeClass
    public void setupData(){
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setUsername(faker.name().username());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1)
    public void testPostUser(){
        logger.debug("***********creating user");
        Response res = UserEndpoints.createUser(userPayload);
        res.then().log().all();
        res.then().statusCode(200);

        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 2)

    public void testGetUserbyName(){
        logger.info("***********reading user");
       Response res= UserEndpoints.readUser(userPayload.getUsername());

       res.then().log().all();
       Assert.assertEquals(res.getStatusCode(), 200);;
    }

    @Test (priority = 3)
    public void testUpdateUserbyName(){
        logger.info("***********updating user");

        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setUsername(faker.name().username());

        Response res = UserEndpoints.updateUser(userPayload.getUsername(), userPayload);
        res.then().log().all();
        Assert.assertEquals(res.getStatusCode(), 200);;

        //after update we have to read the data again and verify

        Response resafterUpdate = UserEndpoints.readUser(userPayload.getUsername());
        Assert.assertEquals(resafterUpdate.getStatusCode(), 200);
    }



    @Test(priority = 4)
    public  void testDeleteUser(){
        logger.info("***********deleting user");
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        Assert.assertEquals(response.getStatusCode(), 200);
    }



    //step 7 is after creating the test cases
    //Create data driven test >>>Excel sheet
    //ExcelUtility file for data driven file with POI dependency
    //
}


