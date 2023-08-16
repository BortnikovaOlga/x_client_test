package ext;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import jakarta.persistence.EntityManager;
import stc_23_06.db.EmployeeRepository;
import stc_23_06.db.EmployeeRepositoryJpa;

public class EmployeeRepositoryJpaResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(EmployeeRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return new EmployeeRepositoryJpa((EntityManager) ExtensionContextManager.getEntityManager(extensionContext));
    }

}
