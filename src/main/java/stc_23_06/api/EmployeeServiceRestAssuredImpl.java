package stc_23_06.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import stc_23_06.model.EmployeeDto;

import java.util.List;

import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class EmployeeServiceRestAssuredImpl implements EmployeeService {
    private final String path = "/employee";
    private final ResponseSpecification getResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();
    private final ResponseSpecification postResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectContentType(ContentType.JSON)
            .build();
    private final ResponseSpecification patchResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();


    @Override
    public List<EmployeeDto> getAll(int companiId) {
        List<EmployeeDto> emp = given().log().all()
                .when()
                .param("company", companiId)
                .get(path)
                .then()
                .spec(getResponseSpec)
                .extract()
                .body().as(new TypeRef<List<EmployeeDto>>() {
                });
        return emp;
    }


    @Override
    public EmployeeDto getById(int employeeId) {
        EmployeeDto emp = given().log().all()
                .when()
                .get(path + "/{id}", employeeId)
                .then().log().all()
                .spec(getResponseSpec)
                .extract()
                .body().as(EmployeeDto.class);
        return emp;
    }

    @Override
    public int create(EmployeeDto emp, String token) {
        int id = given().log().all()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(emp)
                .when()
                .post(path)
                .then().log().all()
                .spec(postResponseSpec)
                .extract()
                .path("id");
        return id;
    }

    @Override
    public EmployeeDto update(EmployeeDto emp, String token) {
        EmployeeDto newEmp = given().log().all()
                .when()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(emp)
                .patch(path + "/{id}", emp.getId())
                .then().log().all()
                .spec(patchResponseSpec)
                .extract()
                .body().as(EmployeeDto.class);
        return newEmp;
    }

}
