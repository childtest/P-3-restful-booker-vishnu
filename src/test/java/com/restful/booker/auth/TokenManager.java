package com.restful.booker.auth;
import com.restful.booker.model.AuthPojo;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by Vishnu Ahir
 */
public class TokenManager extends TestBase {

    public static void generateToken() {

        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");

        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(authPojo)
                .when()
                .post("/auth");
        response.then().statusCode(200);
        response.prettyPrint();

        if (response.getStatusCode() == 200) {
            System.setProperty("token", "token="+response.jsonPath().getString("token"));
            System.out.println("Generated Token: " +System.getProperty("token"));
        } else {
            throw new RuntimeException("Failed to generate token: " + response.prettyPrint());
        }

    }
}