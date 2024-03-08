package com.ait.tests.restAssured;

import com.ait.dto.ContactDto;
import com.ait.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddContactTestsRA extends TestBase{
    @Test
    public void addNewContactSuccessTest(){
        int i = new Random().nextInt(1000)+ 1000;
            //create contact
            ContactDto contactDto = ContactDto.builder()
                    .name("Oliver")
                    .lastName("Kan")
                    .address("Leipzig")
                    .email("oliver" + i + "@gmail.com")
                    .phone("1234567891" + i)
                    .description("goalkeeper")
                    .build();

        String message = given()
                .header("Authorization", TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then().statusCode(200)
                .extract().path("message");
        System.out.println(message);
    }


    @Test
    public void addNewContactWithoutNameTest(){
        int i = new Random().nextInt(1000)+ 1000;
        //create contact
        ContactDto contactDto = ContactDto.builder()
                .lastName("Kan")
                .address("Leipzig")
                .email("oliver" + i + "@gmail.com")
                .phone("1234567891" + i)
                .description("goalkeeper")
                .build();

        ErrorDto errorDto = given()
                .header("Authorization", TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);
        Assert.assertTrue(errorDto.getMessage().toString(). contains("name=must not be blank"));

    }

    @Test
    public void addNewContactInvalidPhoneTest(){
        int i = new Random().nextInt(1000)+ 1000;
        //create contact
        ContactDto contactDto = ContactDto.builder()
                .lastName("Kan")
                .address("Leipzig")
                .email("oliver" + i + "@gmail.com")
                .phone("1234" + i)
                .description("goalkeeper")
                .build();

        given()
                .header("Authorization", TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.phone",
                        containsString("Phone number must contain only digits! And length min 10, max 15!"));


    }

}
