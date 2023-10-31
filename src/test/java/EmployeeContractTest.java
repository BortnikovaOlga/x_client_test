import ext.PropertiesResolver;
import ext.CompanyRepositoryJpaResolver;
import ext.EmployeeRepositoryJpaResolver;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import stc_23_06.api.AuthServiceRestAssuredImpl;
import stc_23_06.api.EmployeeService;
import stc_23_06.api.EmployeeServiceRestAssuredImpl;
import stc_23_06.db.CompanyRepository;
import stc_23_06.db.EmployeeRepository;
import stc_23_06.model.EmployeeDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({EmployeeRepositoryJpaResolver.class,
        CompanyRepositoryJpaResolver.class,
        PropertiesResolver.class
})
public class EmployeeContractTest {
    private final String path = "/employee";
    private final EmployeeService service = new EmployeeServiceRestAssuredImpl();

    private final String token = new AuthServiceRestAssuredImpl().getToken();
    private static EmployeeDto testEmployee;
    private static int idCompany;

    @BeforeAll
    public static void setUp(Properties props, EmployeeRepository repository, CompanyRepository companyRepository) throws SQLException {
        RestAssured.baseURI = props.getProperty("test.url");
        idCompany = companyRepository.create("test_company");
        testEmployee = EmployeeDto.random(idCompany);
        System.out.println(testEmployee);
        testEmployee.setId(repository.create(testEmployee));
    }

    @AfterAll
    public static void tearDown(EmployeeRepository repository, CompanyRepository companyRepository) {
        repository.deleteById(testEmployee.getId());
        companyRepository.deleteById(idCompany);
    }

    @Test
    public void shouldReceiveNotEmptyListForTestCompany() {
        List<EmployeeDto> empList = given().log().all()
                .when()
                .param("company", idCompany)
                .get(path)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .extract()
                .body().as(new TypeRef<List<EmployeeDto>>() {
                });
        assertTrue(empList.size() > 0 && empList.get(0).getCompanyId() == idCompany);
    }

    @Test
    public void shouldReceiveEmptyListIfCompanyIsNotExist() {
        List<EmployeeDto> empList = given().log().all()
                .when()
                .param("company", 1000000)
                .get(path)
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .extract()
                .body().as(new TypeRef<List<EmployeeDto>>() {
                });
        assertEquals(0, empList.size());
    }

    @Test
    public void shouldCreateEmployeeAuthorisedPost(EmployeeRepository repository) {
        EmployeeDto createEmployee = EmployeeDto.random(idCompany);
        int id = given().log().all()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(createEmployee)
                .when()
                .post(path)
                .then().log().all()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .extract()
                .path("id");
        assertTrue(id != 0);
        repository.deleteById(id);
    }

    @Test
    public void shouldReceive401UnauthorisedPost() {
        EmployeeDto createEmployee = EmployeeDto.random(idCompany);
        given().log().all()
                .header("x-client-token", "")
                .contentType(ContentType.JSON)
                .body(createEmployee)
                .when()
                .post(path)
                .then().log().all()
                .statusCode(401);
    }

    @Test
    public void shouldReceive400CreateEmployeeLongFirstName() {
        EmployeeDto createEmployee = EmployeeDto.random(idCompany);
        createEmployee.setFirstName(createEmployee.getFirstName() + "x".repeat(20));
        given().log().all()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(createEmployee)
                .when()
                .post(path)
                .then().log().all()
                .statusCode(400);

    }

    @Test
    public void shouldUpdateEmployeeAuthorisedPatch(){
        EmployeeDto updateEmployee = EmployeeDto.random(idCompany);
        updateEmployee.setId(testEmployee.getId());
        EmployeeDto responseEmp = given().log().all()
                .when()
                .header("x-client-token", token)
                .contentType(ContentType.JSON)
                .body(updateEmployee)
                .patch(path + "/{id}", testEmployee.getId())
                .then().log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Content-Type", containsString("utf-8"))
                .extract()
                .body().as(EmployeeDto.class);
        assertEquals(updateEmployee,responseEmp);
    }

}
