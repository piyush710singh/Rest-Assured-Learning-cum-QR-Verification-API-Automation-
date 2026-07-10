package Day6;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
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


public class SerializationDeseriliaztion {

    @Test
    void convertPOJOtoJSON() throws JsonProcessingException {

        Student st = new Student(); //pojo
        st.setName("Piyush");
        st.setLocation("Delhi");
        st.setPhone("956-637694");
        String[] courses = {"C", "Python"};
        st.setCourses(courses);


        //convert java object >>>Json object (Serialization)

        ObjectMapper objectMapper = new ObjectMapper();
        String jsondata = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(st);
        System.out.println(jsondata);


    }


    @Test
    //de serialization
    void convertJSON2pojo() throws JsonProcessingException {

        String jsondata = "{\n" +
                "  \"name\" : \"Piyush\",\n" +
                "  \"location\" : \"Delhi\",\n" +
                "  \"phone\" : \"956-637694\",\n" +
                "  \"courses\" : [ \"C\", \"Python\" ]\n" +
                "}";

        //converting json in pojo

        ObjectMapper objectMapper = new ObjectMapper();
        Student stu = objectMapper.readValue(jsondata, Student.class);
        System.out.println(stu.getCourses());;
        System.out.println(stu.getName()); ;
        System.out.println(stu.getLocation()); ;
        System.out.println(stu.getPhone()); ;

    }


    }


