import com.github.javafaker.Faker;
import ext.CompanyRepositoryJpaResolver;
import ext.EmployeeRepositoryJpaResolver;
import ext.PropertiesResolver;
import io.restassured.RestAssured;
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
import stc_23_06.model.EmployeeEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith({EmployeeRepositoryJpaResolver.class,
        CompanyRepositoryJpaResolver.class,
        PropertiesResolver.class
})
public class EmployeeBusinessTest {
    private EmployeeService service = new EmployeeServiceRestAssuredImpl();

    private String token = new AuthServiceRestAssuredImpl().getToken();
    private static EmployeeDto testEmployee;
    private static int idCompany;
    Faker faker = new Faker();

    @BeforeAll
    public static void setUp(Properties props, EmployeeRepository repository, CompanyRepository companyRepository) throws SQLException {
        RestAssured.baseURI = props.getProperty("test.url");
        idCompany = companyRepository.create("test_company", "test_company");
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
    public void shouldReceiveListEmployeesHavingTestCompanyId() {
        List<EmployeeDto> empList = service.getAll(idCompany);
        assertTrue(empList.contains(testEmployee));
    }

    @Test
    public void shouldCreateEmployee(EmployeeRepository repository) throws SQLException {
        EmployeeDto createEmployee = EmployeeDto.random(idCompany);

        int idEmp = service.create(createEmployee, token);
        createEmployee.setId(idEmp);

        EmployeeEntity employeeInDb = repository.getById(idEmp);
        boolean isEmployeeInDbEqualCreateEmployee = employeeInDb.isEqualDto(createEmployee);

        repository.deleteById(idEmp);
        assertTrue(isEmployeeInDbEqualCreateEmployee);
    }

    @Test
    public void shouldReceiveEmployeeById() throws SQLException {
        EmployeeDto emp = service.getById(testEmployee.getId());
        assert testEmployee.equals(emp);
    }

    @Test
    public void shouldUpdateEmployeeById(EmployeeRepository repository) {

        EmployeeDto employeeUpdate = EmployeeDto.random(idCompany);
        employeeUpdate.setId(testEmployee.getId());

        EmployeeDto employeeResponse = service.update(employeeUpdate, token);
        EmployeeEntity employeeInDb = repository.getById(testEmployee.getId());

        assertTrue(employeeInDb.isEqualDto(employeeUpdate) && employeeUpdate.equals(employeeResponse));
    }

}
