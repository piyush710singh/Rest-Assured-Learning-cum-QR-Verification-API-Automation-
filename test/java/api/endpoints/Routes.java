package api.endpoints;

public class Routes {

// base url https://petstore.swagger.io/v2--- /pet/{petId}/uploadImage
    // create user (post) /pet
    // get user (get) /pet/{petId}
    //update (put)  /pet
    //delete (delete)   /pet/{petId}



    // create user (post) /user
    // get user (get) /user/{username}
    //update (put)  /user/{username}
    //delete (delete)   /user/{username}



    //User module urls here
    public static String base_url = "https://petstore.swagger.io/v2";

    public static  String post_url = base_url+"/user";
    public static  String get_url = base_url+"/user/{username}";
    public static  String put_url = base_url+"/user/{username}";
    public static  String delete_url = base_url+"/user/{username}";
}
