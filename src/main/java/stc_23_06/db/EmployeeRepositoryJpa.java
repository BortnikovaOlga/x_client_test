package stc_23_06.db;


import jakarta.persistence.EntityManager;
import stc_23_06.model.CompanyEntity;
import stc_23_06.model.EmployeeDto;
import stc_23_06.model.EmployeeEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeRepositoryJpa implements EmployeeRepository {
    private final EntityManager entityManager;

    public EmployeeRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EmployeeEntity> getAll() {
        return entityManager
                .createQuery("SELECT e FROM EmployeeEntity e", EmployeeEntity.class)
                .getResultList();
    }

    @Override
    public EmployeeEntity getById(int id) {
        return entityManager.find(EmployeeEntity.class, id);
    }

    @Override
    public int create(EmployeeDto emp) {
        EmployeeEntity newEmployeer = new EmployeeEntity(emp);
        newEmployeer.setCreateDateTime(Timestamp.valueOf(LocalDateTime.now()));
        newEmployeer.setLastChangedDateTime(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.getTransaction().begin();
        entityManager.persist(newEmployeer);
        entityManager.getTransaction().commit();
        return newEmployeer.getId();
    }

    @Override
    public void deleteById(int id) {
        EmployeeEntity entity = entityManager.find(EmployeeEntity.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }
}
