package com.sure.orangehrm.api;

import io.restassured.response.Response;
import java.util.Map;

public class EmployeeApi {

    public static Response createEmployee(Map<String, Object> payload) {
        Response response = null;
        try {
            response = ApiClient.baseRequest()
                    .body(payload)
                    .post("pim/employees")
                    .then()
                    .extract()
                    .response();
        } catch (Exception e) {
            System.out.println("Failed to create employee");
            e.printStackTrace();
        }

        return response;
    }
}
