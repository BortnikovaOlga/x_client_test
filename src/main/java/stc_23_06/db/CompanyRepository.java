package stc_23_06.db;

import stc_23_06.model.CompanyEntity;

import java.sql.SQLException;
import java.util.List;

public interface CompanyRepository {

    List<CompanyEntity> getAll() throws SQLException;

    List<CompanyEntity> getAll(boolean isActive);

    public CompanyEntity getByName(String name);

    CompanyEntity getLast() throws SQLException;

    CompanyEntity getById(int id);

    int create(String name) throws SQLException;

    //    int create(String name, String description) throws SQLException;
    public CompanyEntity create_(String nameToBe);

    void deleteById(int id);

    void refresh(CompanyEntity company);
}