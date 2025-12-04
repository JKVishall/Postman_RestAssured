package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import serializationMethodsDemo.locationInfo;
import serializationMethodsDemo.websiteDetails;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializationDemo {

    @Test
    public void serializationDemo(){
        websiteDetails bodyData = new websiteDetails();
        bodyData.setAccuracy(50);
        bodyData.setName("Frontline house");
        bodyData.setPhoneNumber("(+91) 983 893 3937");
        bodyData.setAddress("29, side layout, cohen 09");
        bodyData.setWebsite("http://google.com");
        bodyData.setLanguage("French-IN");

        //since setTypes method requires a list of strings, we are creating a list of string and then add values to it and then send
        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        bodyData.setTypes(types);

        //since setLocation expects an object to be sent in it (locationInfo class object)
        //we need to create an object of locationInfo class and then send it inside setLocation
        locationInfo ll = new locationInfo();
        ll.setLatitude(-38.383494);
        ll.setLongitude(33.427362);

        bodyData.setLocation(ll);

        //now, instead of the body, we can send the bodyData object and it will serialize it into the expected Json on its own
        RestAssured.baseURI="https://www.rahulshettyacademy.com";
        RestAssured.useRelaxedHTTPSValidation();
        Response response = given().queryParam("key", "qaclick123")
                .contentType("application/json")
                .body(bodyData)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println(response.statusCode());
    }



}
