package com.sure.orangehrm.api;

import io.restassured.response.Response;

import java.util.Map;

public class EmployeeApi {


    public Response createEmployee(Map<String, Object> employeeData) {
        Response response = ApiClient.post("pim/employees", employeeData);

        int status = response.getStatusCode();
        if (status != 200 && status != 201) {
            throw new IllegalStateException(
                    "Failed to create employee, status: " + status + ", body: " + response.asString());
        }

        return response;
    }
}
