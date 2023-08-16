package ext;

import ext.prop.PropertyProvider;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.extension.ExtensionContext;
import stc_23_06.db.MyPersistenceUnitInfo;

import java.util.Properties;

public class ExtensionContextManager {
    public static Object getEntityManager(ExtensionContext extensionContext) {


        Object em = extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get("em");

        if (em == null) {
            Properties props = PropertyProvider.getInstance().getProps();
            PersistenceUnitInfo persistenceUnitInfo = new MyPersistenceUnitInfo(props);

            HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
            EntityManagerFactory factory =
                    hibernatePersistenceProvider
                            .createContainerEntityManagerFactory(persistenceUnitInfo, props);
            em = factory.createEntityManager();

            extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).put("em", em);
        }
        return em;
    }
}
