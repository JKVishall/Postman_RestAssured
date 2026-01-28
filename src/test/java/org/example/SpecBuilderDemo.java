package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import serializationMethodsDemo.LocationInfo;
import serializationMethodsDemo.WebsiteDetails;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderDemo {

    @Test
    public void serializationDemo(){
        WebsiteDetails bodyData = new WebsiteDetails();
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
        LocationInfo ll = new LocationInfo();
        ll.setLatitude(-38.383494);
        ll.setLongitude(33.427362);

        bodyData.setLocation(ll);

        //now, instead of the body, we can send the bodyData object and it will serialize it into the expected Json on its own

        //Doing RequestSpecBuilder
        //This will include the baseURI and all other things we give in given() except .body
        //we need to create a new object of RequestSpecBuilder
        //need to give .build() for it to build it to the specBuilder type
        //given()
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();
        RequestSpecification reqSpec = given().spec(requestSpec).body(bodyData);

        //Doing ResponseSpecBuilder
        //This will include all the things we give on then()
        //then()
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


        //Final code
        //when()
        Response response = reqSpec.when().post("maps/api/place/add/json").then().spec(responseSpec).extract().response();
        System.out.println(response.statusCode());
        System.out.println(response.asString());
//        RestAssured.baseURI="https://www.rahulshettyacademy.com";
//        RestAssured.useRelaxedHTTPSValidation();
//        Response response = given().queryParam("key", "qaclick123")
//                .contentType("application/json")
//                .body(bodyData)
//                .when().post("maps/api/place/add/json")
//                .then().assertThat().statusCode(200).extract().response();
       // System.out.println(response.statusCode());
    }



}
