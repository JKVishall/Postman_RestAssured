package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloadFiles.ReusableMethods;
import payloadFiles.createPayload;

public class ComplexDemo extends ReusableMethods {

    @Test
    public static void complexDemoTestCases(){

        JsonPath jj = new JsonPath(createPayload.payloadForComplexDemo());

        //TC01: Print No of courses returned by API
        int numOfCourses = jj.getInt("courses.size()");
        System.out.println(numOfCourses);

        //TC02: Print Purchase Amount
        String totalPurchaseAmount = jj.getString("dashboard.purchaseAmount");
        System.out.println(totalPurchaseAmount);

        //TC03: Print Title of the first course
        String firstTitle = jj.getString("courses[0].title");
        System.out.println(firstTitle);
        //the 0 in courses[0] line 22, can be replaced with the variable of our wish
        //Let's see that below with the help of a for loop
        for(int i=0;i<numOfCourses;i++){
            String allTitles = jj.getString("courses["+i+"].title");
            System.out.println(allTitles);
        }

        //TC04: Print All course titles and their respective Prices
        for(int i =0;i<numOfCourses;i++){
            System.out.println(jj.getString("courses["+i+"].title"));
            System.out.println(jj.getString("courses["+i+"].price"));
        }

        //TC05: Print no of copies sold by RPA Course
        for (int i =0;i<numOfCourses;i++){
            if (jj.getString("courses["+i+"].title").equalsIgnoreCase("RPA")){
                System.out.println(jj.getString("courses["+i+"].copies"));
                break;
            }
        }

        //TC06: Verify if Sum of all Course prices matches with Purchase Amount
        int sumPriceOfAllCourses = 0;
        for (int i=0;i<numOfCourses;i++){
            int tempPrice = jj.getInt("courses["+i+"].price");
            sumPriceOfAllCourses = sumPriceOfAllCourses + tempPrice;
        }
        Assert.assertEquals(sumPriceOfAllCourses, totalPurchaseAmount);
    }
}
