package com.restful.booker.testsuite;

import com.restful.booker.constant.EndPoints;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingExtraction extends TestBase {

    static int bookingId = 0;

    @Test
    public void extractBookingData() {

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


        System.out.println("First Name : " + response.extract().path("firstname"));
        System.out.println("Last Name : " + response.extract().path("lastname"));
        System.out.println("Total Price : " + response.extract().path("totalprice"));
        System.out.println("Deposit Paid : " + response.extract().path("depositpaid"));
        System.out.println("Check-in Date : " + response.extract().path("bookingdates.checkin"));
        System.out.println("Check-out Date : " + response.extract().path("bookingdates.checkout"));
        System.out.println("Additional Needs : " + response.extract().path("additionalneeds"));
    }
}
