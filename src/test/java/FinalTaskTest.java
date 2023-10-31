import ext.*;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import stc_23_06.api.*;
import stc_23_06.db.CompanyRepository;
import stc_23_06.db.EmployeeRepository;
import stc_23_06.model.CompanyDto;
import stc_23_06.model.CompanyEntity;
import stc_23_06.model.EmployeeDto;
import stc_23_06.model.ErrorResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        PropertiesResolver.class,
        EmployeeRepositoryJpaResolver.class,
        EmployeeRestAssuredServiceResolver.class,
        CompanyRepositoryJpaResolver.class,
        CompanyRestAssuredServiceResolver.class
})
public class FinalTaskTest {

    private final String token = new AuthServiceRestAssuredImpl().getToken();
    private static int idCompany;
    private static int activeEmployeeId;
    private static int notActiveEmployeeId;

    @Step("BeforeAll")
    @BeforeAll
    public static void setUp(Properties props, CompanyRepository companyRepository,
                             EmployeeRepository employeeRepository) throws SQLException {
        RestAssured.baseURI = props.getProperty("test.url");

        idCompany = companyRepository.create("test_company");
        activeEmployeeId = employeeRepository.create(EmployeeDto.random(idCompany));
        EmployeeDto emp = EmployeeDto.random(idCompany);
        emp.setActive(false);
        notActiveEmployeeId = employeeRepository.create(emp);

    }

    @Step("AfterAll")
    @AfterAll
    public static void tearDown(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        employeeRepository.deleteById(activeEmployeeId);
        employeeRepository.deleteById(notActiveEmployeeId);
        companyRepository.deleteById(idCompany);
    }

    @Test
    @DisplayName("Проверка, что список компаний фильтруется по параметру active=true")
    public void testShouldFilteredCompaniesHavingIsActiveTrue(CompanyRepository repository, CompanyService service) throws IOException {
        List<CompanyDto> api = service.getAll(true);
        List<CompanyEntity> db = repository.getAll(true);

        assertThat(db.size()).isEqualTo(api.size());
        assertThat(api.get(0).isActive()).isTrue();
    }

    @Test
    @DisplayName("Проверить, что список компаний фильтруется по параметру active=false")
    public void testShouldFilteredCompaniesHavingIsActiveFalse(CompanyRepository repository, CompanyService service) throws IOException {
        List<CompanyDto> api = service.getAll(false);
        List<CompanyEntity> db = repository.getAll(false);

        assertThat(db.size()).isEqualTo(api.size());
        assertThat(api.get(0).isActive()).isFalse();
    }

    @Test
    @DisplayName("Проверить создание сотрудника в несуществующей компании")
    public void testShouldNotCreateEmployeeForNotExistCompany(EmployeeService service) {
        ErrorResponse error = service.errorCreate(EmployeeDto.random(9999999), token);

        assertThat(500).isEqualTo(error.getStatusCode());
    }

    @Test
    @DisplayName("Проверить, что у удаленной компании проставляется в БД поле deletedAt")
    public void testShouldNotHaveNullDeleteAtAfterDelete(CompanyRepository repository, CompanyService service) throws InterruptedException {
        CompanyEntity company = repository.create_("test_new_company");
        service.deleteById(company.getId(), token);
        TimeUnit.SECONDS.sleep(7);
        repository.refresh(company);
        repository.deleteById(company.getId());

        assertThat(company.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("Проверка, что неактивный сотрудник не отображается в списке")
    public void testShouldNotHaveDeactivateEmployeeInCompanyList(EmployeeService service) {
        List<EmployeeDto> getAll = service.getAll(idCompany);
        List<EmployeeDto> filtered = getAll.stream().filter(EmployeeDto::isActive).toList();

        assertThat(getAll).isEqualTo(filtered);
    }

}


