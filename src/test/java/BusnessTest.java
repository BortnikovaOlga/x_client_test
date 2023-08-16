import ext.CompanyRepositoryJpaResolver;
import ext.EmployeeRepositoryJpaResolver;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith({EmployeeRepositoryJpaResolver.class,
        CompanyRepositoryJpaResolver.class
})
public class BusnessTest {
    private EmployeeService service = new EmployeeServiceRestAssuredImpl();

    private String token = new AuthServiceRestAssuredImpl().getToken();
    private static EmployeeDto testEmployee;
    private static int idCompany;

    @BeforeAll
    public static void setUp(EmployeeRepository repository, CompanyRepository companyRepository) throws SQLException {
        RestAssured.baseURI = "https://x-clients-be.onrender.com";
        idCompany = companyRepository.create("test_company", "test_company");
        testEmployee = new EmployeeDto(true, "X2", "X", "X", "111", "x@x.com", "url", idCompany);
        testEmployee.setId(repository.create(testEmployee));
    }
    @AfterAll
    public static void tearDown(EmployeeRepository repository, CompanyRepository companyRepository){
        repository.deleteById(testEmployee.getId());
        companyRepository.deleteById(idCompany);
    }

    @Test
    public void shouldReceiveListOnGetAllRequest() {
        List<EmployeeDto> empList = service.getAll(idCompany);
        assertEquals(idCompany,empList.get(0).getCompanyId());
    }

    @Test
    public void shouldReceive201OnPost(EmployeeRepository repository) throws SQLException {
        EmployeeDto employee = new EmployeeDto(true, "X2", "X", "X", "111", "x@x.com", "url", idCompany);
        int idEmployee = service.create(employee, token);
        employee.setId(idEmployee);
        System.out.println(idEmployee);
        EmployeeEntity repoEmployee = repository.getById(idEmployee);
        assertTrue(repoEmployee.isEqualDto(employee));
    }

    @Test
    public void shouldReceive200OnGetByIdRequest() throws SQLException {
        EmployeeDto emp = service.getById(testEmployee.getId());
        assert testEmployee.equals(emp);
    }

}
