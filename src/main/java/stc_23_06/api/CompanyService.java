package stc_23_06.api;

import io.qameta.allure.Step;
import stc_23_06.model.CompanyDto;
import stc_23_06.model.CreateCompanyResponse;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAll() throws IOException;

    List<CompanyDto> getAll(boolean isActive) throws IOException;

    CompanyDto getById(int id) throws IOException;

    int create(String name, String token) throws IOException;

    int create(String name, String description, String token) throws IOException;

    CompanyDto deleteById(int id, String token);

    CompanyDto edit(int id, String newName);

    CompanyDto edit(int id, String newName, String newDescription);

    CompanyDto changeStatus(int id, boolean isActive);

}