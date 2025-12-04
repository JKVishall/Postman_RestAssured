package org.example;

import EcommerceDemoRequestResponseClasses.AddProductResponse;
import EcommerceDemoRequestResponseClasses.LoginRequest;
import EcommerceDemoRequestResponseClasses.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class EcommerceDemo {

    String token;
    String userId;
    String productId;

    @Test
    public void eCommercePOSTWithBodyParameter(){
        RestAssured.useRelaxedHTTPSValidation();

        //POST with body
        RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        LoginRequest bodyData = new LoginRequest();
        bodyData.setUserEmail("testBat@gmail.com");
        bodyData.setUserPassword("Test@123");

       RequestSpecification reqSpec = given().spec(requestSpec).body(bodyData);

       LoginResponse loginResponse = reqSpec.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);

        token = loginResponse.getToken();
        System.out.println(token);
        userId = loginResponse.getUserId();
        System.out.println(userId);

    }

    @Test
    public void postWithFormParameters(){
        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();
        RequestSpecification reqSpec = given().spec(requestSpecification)
                .param("productName", "Laptop")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Lenova").param("productFor", "men")
                .multiPart("productImage",new File("C:\\Users\\UU489ZG\\OneDrive - EY\\Documents\\Automation\\PostMan_RestAssured\\src\\test\\Resources\\0081_RESOURCE_KeyBASkills.png"));

        AddProductResponse addProductResponse = reqSpec.when().post("/api/ecom/product/add-product")
                .then().extract().response().as(AddProductResponse.class);
        productId = addProductResponse.getProductId();
        System.out.println("Product ID: " + productId);

    }
}
