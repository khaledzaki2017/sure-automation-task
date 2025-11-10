package com.sure.orangehrm.api;

import io.restassured.response.Response;

import java.util.Map;

public class PersonalDetailsApi {


    public Response updatePersonalDetails(String empNumber, Map<String, Object> personalData) {
        if (empNumber == null || empNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("empNumber must not be null or empty");
        }

        String path = String.format("pim/employees/%s/personal-details", empNumber);
        Response response = ApiClient.put(path, personalData);

        int code = response.getStatusCode();
        if (code != 200 && code != 204) {
            throw new IllegalStateException(
                    "Failed to update personal details, status: " + code + ", body: " + response.asString());
        }

        return response;
    }
}
