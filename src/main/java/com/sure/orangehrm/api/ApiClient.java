package com.sure.orangehrm.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import com.sure.orangehrm.utils.ConfigReader;

public class ApiClient {

    public static RequestSpecification baseRequest() {
        String baseUri = ConfigReader.get("api.base");

        if (baseUri == null) {
            System.out.println("Base URI is not set in config!");
            baseUri = "http://localhost";
        }

        RestAssured.baseURI = baseUri;

        RequestSpecification request = RestAssured.given();
        request.contentType("application/json");

        return request;
    }
}
