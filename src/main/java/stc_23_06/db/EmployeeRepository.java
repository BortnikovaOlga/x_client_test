package stc_23_06.db;

import stc_23_06.model.EmployeeDto;
import stc_23_06.model.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeEntity> getAll();

    EmployeeEntity getById(int id);

    int create(EmployeeDto emp);

    void deleteById(int id);
}
