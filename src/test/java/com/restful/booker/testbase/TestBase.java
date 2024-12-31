package com.restful.booker.testbase;

import com.restful.booker.utils.PropertyReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

import static com.restful.booker.auth.TokenManager.generateToken;

public class TestBase {

    public static PropertyReader propertyReader;

    @BeforeMethod
    public void inIT() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");

        if (System.getProperty("token") == null || System.getProperty("token").isEmpty()) {
            generateToken();
        }
    }

}