package stc_23_06.api;

import stc_23_06.model.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll(int companyId);
    EmployeeDto getById(int employeeId);
    int create(EmployeeDto emp, String token );
    EmployeeDto edit(EmployeeDto emp);
    EmployeeDto changeStatus(int id, boolean isActive);
}
