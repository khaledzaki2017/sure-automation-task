package com.sure.orangehrm.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiClient {

    private static final String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/";
    private static String sessionCookie;

    public static void setSessionCookie(String cookie) {
        sessionCookie = cookie;
        System.out.println("[ApiClient] Session cookie set: " + sessionCookie);
    }

    public static String getSessionCookie() {
        return sessionCookie;
    }

    private static RequestSpecification baseRequest() {
        RequestSpecification req = RestAssured.given()
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .relaxedHTTPSValidation();

        if (sessionCookie != null && !sessionCookie.isEmpty()) {
            req.header("Cookie", sessionCookie);
        }
        return req;
    }

    public static Response post(String path, Map<String, Object> body) {
        RequestSpecification request = baseRequest();
        if (body != null) {
            request.body(body);
        }
        return request.when().post(path);
    }

    public static Response put(String path, Map<String, Object> body) {
        RequestSpecification request = baseRequest();
        if (body != null) {
            request.body(body);
        }
        return request.when().put(path);
    }
}
