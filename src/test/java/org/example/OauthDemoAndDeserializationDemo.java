package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import deserializationMethodsDemo.courseDetail;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OauthDemoAndDeserializationDemo {
    @Test
    public void oAuthDemo(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        RestAssured.useRelaxedHTTPSValidation();

        //Getting the oAuth token using Post
        String oauthResponse = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(oauthResponse);
        JsonPath jj = new JsonPath(oauthResponse);
        String accessToken = jj.getString("access_token");
        System.out.println(accessToken);

        //Using Get to get the required detail from the server
        courseDetail getResponse = given().queryParam("access_token",accessToken)
                .when().get("/oauthapi/getCourseDetails").as(courseDetail.class);

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("Selenium Webdriver Java");
        expectedResult.add("Cypress");
        expectedResult.add("Protractor");

        System.out.println(getResponse.getInstructor());



        int webAutomationPlans = getResponse.getCourses().getWebAutomation().size();

        List<String> actualResults = new ArrayList<>();
        for (int i =0;i<webAutomationPlans;i++){
            System.out.println(getResponse.getCourses().getWebAutomation().get(i).getCourseTitle());
            System.out.println(getResponse.getCourses().getWebAutomation().get(i).getPrice());

            actualResults.add(getResponse.getCourses().getWebAutomation().get(i).getCourseTitle());
        }

        Assert.assertEquals(actualResults, expectedResult);


    }
}
