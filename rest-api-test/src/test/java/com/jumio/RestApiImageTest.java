package com.jumio;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestApiImageTest {

    private String json = "{\"image\": \"test\"}";
    private String baseuRL = "http://localhost:8080/api";

    @Test
    public void base64ImageTest(){
        Response response = RestAssured.given().
                contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseuRL + "/image-base64");

        Assert.assertEquals(response.getStatusCode(),200);
        response.then().body("image", Matchers.is("dGVzdA=="));
    }

    @Test
    public void basicImageTest(){
        Response response = RestAssured.given().
                contentType(ContentType.JSON)
                .body(json)
                .when()
                .post(baseuRL + "/image");

        Assert.assertEquals(response.getStatusCode(),200);
        response.then().body("image", Matchers.is("test"));
    }
}
