package com.restful.booker.crudtest;

import com.restful.booker.constant.EndPoints;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookingCRUDTest extends TestBase {

    static int bookingId = 0;

    String fName = "child" + TestUtils.getRandomValue();
    String lName = "test";
    int totalPrice = 500;
    String additionalNeeds = "Lunch";


    @Test(priority = 1)
    public void createBooking() {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName);
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(true);
        HashMap<String, String> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        bookingPojo.setBookingdates(dates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        ValidatableResponse response = given()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .body(bookingPojo)
                .when()
                .post(EndPoints.BOOKING)
                .then().statusCode(200);

        bookingId = response.extract().path("bookingid");
        System.out.println("Booking id : " + bookingId);

    }

    @Test(priority = 2)
    public void getBookingById() {

        ValidatableResponse response = given().log().ifValidationFails()
                .pathParams("bookingId", bookingId)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

    }

    @Test(priority = 3)
    public void updateBookingById() {

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName + " Updated name " + TestUtils.getRandomValue());
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(true);
        HashMap<String, String> dates = new HashMap<>();
        dates.put("checkin", "2018-01-01");
        dates.put("checkout", "2019-01-01");
        bookingPojo.setBookingdates(dates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        Response response = given().log().ifValidationFails()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("cookie", System.getProperty("token"))
                .pathParams("bookingId", bookingId)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID);
        response.then().log().ifValidationFails().statusCode(200);

    }

    @Test(priority = 4)
    public void deleteBookingById() {

        Response response = given().log().all()
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .header("cookie", System.getProperty("token"))
                .pathParams("bookingId", bookingId)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID);
        response.then().statusCode(201);

        given().log().ifValidationFails()
                .pathParams("bookingId", bookingId)
                .when()
                .get(EndPoints.GET_BOOKING_BY_ID)
                .then().log().ifValidationFails().statusCode(404);
    }

}
