package stc_23_06.api;

import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import stc_23_06.model.CompanyDto;
import stc_23_06.model.CreateCompanyRequest;
import io.qameta.allure.Step;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CompanyServiceRestAssuredImpl implements CompanyService {
    private final String path = "/company";
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
    private final ResponseSpecification deleteResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();

    @Override
    public List<CompanyDto> getAll() throws IOException {
        Allure.step("вызов api получение списка всех компаний");
        return given().log().all().filter(new AllureRestAssured())
                .when()
                .get(path)
                .then()
                .spec(getResponseSpec)
                .extract()
                .body().as(new TypeRef<List<CompanyDto>>() {
                });
    }


    @Override
    public List<CompanyDto> getAll(boolean isActive) throws IOException {
        Allure.step("вызов api получение списка компаний");
        Allure.parameter("active=", isActive);
        return given().log().all().filter(new AllureRestAssured())
                .when()
                .param("active", Boolean.toString(isActive))
                .get(path)
                .then()
                .spec(getResponseSpec)
                .extract()
                .body().as(new TypeRef<List<CompanyDto>>() {
                });
    }

    @Override
    public CompanyDto getById(int companyId) throws IOException {
        Allure.step("вызов api получение компании по ИД");
        Allure.parameter("id=", companyId);
        return given().log().all().filter(new AllureRestAssured())
                .when()
                .get(path + "/{id}", companyId)
                .then().log().all()
                .spec(getResponseSpec)
                .extract()
                .body().as(CompanyDto.class);
    }

    @Override
    public int create(String name, String token) throws IOException {
        Allure.step("вызов api создание компании");
        Allure.parameter("name=", name);
        return given().log().all().filter(new AllureRestAssured())
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(name))
                .when()
                .post(path)
                .then().log().all()
                .spec(postResponseSpec)
                .extract()
                .path("id");
    }

    @Override
    public int create(String name, String description, String token) throws IOException {
        return given().log().all().filter(new AllureRestAssured())
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(new CreateCompanyRequest(name, description))
                .when()
                .post(path)
                .then().log().all()
                .spec(postResponseSpec)
                .extract()
                .path("id");
    }

    @Override
    public CompanyDto deleteById(int companyId, String token) {
        Allure.step("вызов api удаление компании");
        Allure.parameter("id=", companyId);
        return given().log().all().filter(new AllureRestAssured())
                .header("x-client-token", token)
                .when()
                .get(path + "/delete/{id}", companyId)
                .then().log().all()
                .spec(deleteResponseSpec)
                .extract()
                .body().as(CompanyDto.class);
    }

    @Override
    public CompanyDto edit(int id, String newName) {
        return null;
    }

    @Override
    public CompanyDto edit(int id, String newName, String newDescription) {
        return null;
    }

    @Override
    public CompanyDto changeStatus(int id, boolean isActive) {
        return null;
    }
}
