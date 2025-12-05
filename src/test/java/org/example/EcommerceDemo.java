package org.example;

import EcommerceDemoRequestResponseClasses.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                .param("productName", "Desktop")
                .param("productAddedBy", userId).param("productCategory", "fashion")
                .param("productSubCategory", "shirts").param("productPrice", "11500")
                .param("productDescription", "Lenova").param("productFor", "men")
                .multiPart("productImage",new File("C:\\Users\\UU489ZG\\OneDrive - EY\\Documents\\Automation\\PostMan_RestAssured\\src\\test\\Resources\\0081_RESOURCE_KeyBASkills.png"));

        //Unlike the 1st test case where we directly extract this api call as customclass(AddProductResponse.class) object,
        //here we extract it as Response first inorder to validate the status code
        //then we are converting the response again to customclass object
        Response response = reqSpec.when().post("/api/ecom/product/add-product")
                .then().extract().response();
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        AddProductResponse addProductResponse = response.as(AddProductResponse.class);

        productId = addProductResponse.getProductId();
        System.out.println("Product ID: " + productId);

    }

    @Test
    public void postWithHTTPSRelaxed(){
        //We are ordering the product here
       RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
               .addHeader("authorization", token)
               .setContentType(ContentType.JSON)
               .build();

        CreateOrderDetailsPartTwo orderDetail = new CreateOrderDetailsPartTwo();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<CreateOrderDetailsPartTwo> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        CreateOrderRequest crd = new CreateOrderRequest();
        crd.setOrders(orderDetailList);

        RequestSpecification reqSpec = given().relaxedHTTPSValidation().spec(requestSpecification).body(crd);

        Response response = reqSpec.when().post("/api/ecom/order/create-order").then().extract().response();
        System.out.println(response.getStatusCode());

        System.out.println(response.asString());
    }

    @Test(priority = 4)
    public void deleteItemWithPathParametersInURL(){
        RequestSpecification deleteRequestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        RequestSpecification deleteReqSpec = given().spec(deleteRequestSpec).pathParam("productId", productId);

        String deleteResponseString = deleteReqSpec.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
                .extract().asString();
        JsonPath js = new JsonPath(deleteResponseString);
        Assert.assertEquals(js.getString("message"), "Product Deleted Successfully");
    }
}
