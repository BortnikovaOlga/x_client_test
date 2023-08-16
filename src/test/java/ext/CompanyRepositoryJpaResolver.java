package ext;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.*;
import stc_23_06.db.*;


public class CompanyRepositoryJpaResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CompanyRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return new CompanyRepositoryJpa((EntityManager) ExtensionContextManager.getEntityManager(extensionContext));
    }

}