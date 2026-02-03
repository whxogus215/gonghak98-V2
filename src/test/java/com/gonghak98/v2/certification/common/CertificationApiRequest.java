package com.gonghak98.v2.certification.common;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

public class CertificationApiRequest {

    public static ExtractableResponse<Response> ABEEK_결과_조회_요청(String requestBody) {
        return RestAssured.given().log().all()
                          .body(requestBody).contentType(MediaType.APPLICATION_JSON_VALUE)
                          .when().post("/api/certification/check")
                          .then().extract();
    }

}
