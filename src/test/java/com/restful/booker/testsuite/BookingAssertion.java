package com.restful.booker.testsuite;

import com.restful.booker.constant.EndPoints;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class BookingAssertion extends TestBase {

    static int bookingId = 0;

    @Test
    public void verifyBookingData() {

        ValidatableResponse response1 = given()
                .when()
                .get(EndPoints.BOOKING)
                .then().statusCode(200);
        bookingId = response1.extract().path("[0].bookingid");
        System.out.println("Booking id : " + bookingId);


        ValidatableResponse response = given().log().ifValidationFails()
                .pathParams("bookingId", bookingId)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        response.body("$", hasKey("firstname"),
                "$", hasKey("lastname"),
                "$", hasKey("totalprice"),
                "$", hasKey("depositpaid"),
                "$", hasKey("bookingdates"),
                "$", hasKey("additionalneeds"),
                "bookingdates", hasKey("checkin"),
                "bookingdates", hasKey("checkout"));
    }

}
