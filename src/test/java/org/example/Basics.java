package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import payloadFiles.ReusableMethods;
import payloadFiles.createPayload;
import  static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Basics extends ReusableMethods {

    public static void main(String[] args){

        //given - aside from the two things we sent inside when, everything else will go inside given
        //when - Resource path and http method will go under when
        //Then - the expected output should go here
        RestAssured.baseURI="https://rahulshettyacademy.com";

        RestAssured.useRelaxedHTTPSValidation();


        //POST
        //header-application/json must be given if our body is going to be of json type
        String postResponse = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(createPayload.postPayload())
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().response().asString();

        //Since the main method is static, we need to make the reusable method static as wel
        String placeID = ReusableMethods.rawToJsonAndGetString(postResponse, "place_id");
        System.out.println("\n Result of POST operation is giving placeID as: "+placeID);


        //PUT
        String newAddress = "John Mason Street, India";
        String putResponse = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(createPayload.putPayload(placeID, newAddress))
                .when().put("maps/api/place/update/json")
                .then().assertThat().body("msg", equalTo("Address successfully updated"))
                .extract().response().asString();
        String putOutput = String.valueOf(ReusableMethods.rawToJsonAndGetString(putResponse, "msg"));
        System.out.println("\n Result of PUT operation: "+putOutput);


        //GET
        String getResponse = given().queryParam("key","qaclick123").queryParam("place_id",placeID)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        String getOutput = String.valueOf(ReusableMethods.rawToJsonAndGetString(getResponse, "address"));
        System.out.println("\n Result of GET operation: "+getOutput);

        Assert.assertEquals(getOutput, newAddress);
    }

}
