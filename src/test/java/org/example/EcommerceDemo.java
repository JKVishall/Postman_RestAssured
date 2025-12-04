package org.example;

import EcommerceDemoRequestResponseClasses.LoginRequest;
import EcommerceDemoRequestResponseClasses.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class EcommerceDemo {

    @Test
    public void eCommercePOST(){


        RestAssured.useRelaxedHTTPSValidation();

        //POST with body
        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        LoginRequest bodyData = new LoginRequest();
        bodyData.setUserEmail("rahulshetty@gmail.com");
        bodyData.setUserPassword("Iamking@000");

       RequestSpecification reqSpec = given().spec(requestSpec).body(bodyData);

       LoginResponse loginResponse = reqSpec.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);

        System.out.println(loginResponse.getToken());
        System.out.println(loginResponse.getUserId());

        //POST with Form parameters

    }
}
