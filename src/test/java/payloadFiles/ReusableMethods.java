package payloadFiles;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payloadFiles.createPayload;
import  static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class ReusableMethods {
    public static String rawToJsonAndGetString(String Response, String requiredValue){
        JsonPath js = new JsonPath(Response);
        String responseString = js.getString(requiredValue);
        return responseString;
    }

//    public static String rawToJsonAndGetInt(String Response, String requiredValue){
//        JsonPath js = new JsonPath(Response);
//        String responseString = js.getInt(requiredValue);
//        return responseString;
//    }



    public  static JsonPath rawToJson(String Response){
        JsonPath jk = new JsonPath(Response);
        return jk;
    }
}
