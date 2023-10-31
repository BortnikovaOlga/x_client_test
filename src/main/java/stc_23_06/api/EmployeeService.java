package stc_23_06.api;

import stc_23_06.model.EmployeeDto;
import stc_23_06.model.ErrorResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAll(int companyId);
    EmployeeDto getById(int employeeId);
    int create(EmployeeDto emp, String token );
    ErrorResponse errorCreate(EmployeeDto emp, String token);
    EmployeeDto update(EmployeeDto emp, String token);

    }
