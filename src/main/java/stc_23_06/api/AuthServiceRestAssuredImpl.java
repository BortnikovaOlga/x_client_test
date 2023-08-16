package stc_23_06.api;

import io.restassured.http.ContentType;
import stc_23_06.model.AuthDto;

import static io.restassured.RestAssured.given;

public class AuthServiceRestAssuredImpl implements AuthService {
    private final String path = "/auth";

    @Override
    public String getToken() {
        String tocken = given()
                .basePath(path + "/login")
                .contentType(ContentType.JSON)
                .body(new AuthDto("roxy", "animal-fairy"))
                .post()
                .then()
                .statusCode(201)
                .extract().path("userToken");
        return tocken;
    }
}
