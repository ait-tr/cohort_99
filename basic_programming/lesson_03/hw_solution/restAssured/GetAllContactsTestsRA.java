package com.ait.tests.restAssured;

import com.ait.dto.ContactDto;
import com.ait.dto.GetAllContactsDTO;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetAllContactsTestsRA extends TestBase{
    @Test
    public void getAllContactsSuccessTest(){
        GetAllContactsDTO responseDto = given()
                .header("Authorization", TOKEN)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDTO.class);
        for(ContactDto contact: responseDto.getContacts()){
            System.out.println(contact.getId() + "***" + contact.getName()); //ввожу неправильный id в виде снежинок
            System.out.println("====================================");
        }
    }

    @Test
    public void getAllContactsErrorTest(){
        given()
                .header("Authorization", "hjkll'") //ввожу неправильный токен
                .when()
                .get("contacts")
                .then()
        .assertThat().statusCode(401)
                .assertThat().body("error", equalTo("Unauthorized"));

    }
}
