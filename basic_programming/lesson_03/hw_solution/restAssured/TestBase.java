package com.ait.tests.restAssured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibmF0YUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5OTk5NzIyOSwiaWF0IjoxNjk5Mzk3MjI5fQ.j6HXlNAY2m3PmHOBJUgwWLJ0q_uae5H3L7_Hv1xLezo";

    @BeforeMethod
    public void init(){  //тот же precondition
        System.err.close(); //закрываем все системные ошибки

        RestAssured.baseURI ="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";  //basePath - продолжение пути в виде переменной, преобразованного в переменную

    }
}
