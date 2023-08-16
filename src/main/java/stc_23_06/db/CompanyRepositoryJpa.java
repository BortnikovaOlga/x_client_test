package stc_23_06.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import stc_23_06.model.CompanyEntity;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


public class CompanyRepositoryJpa implements CompanyRepository {
    private final EntityManager entityManager;

    public CompanyRepositoryJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CompanyEntity> getAll() {
        TypedQuery<CompanyEntity> selectAll = entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.deletedAt is not null", CompanyEntity.class);
        return selectAll.getResultList();
    }

    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        TypedQuery<CompanyEntity> selectAll = entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.deletedAt is not null AND c.isActive=:active", CompanyEntity.class);
        selectAll.setParameter("active", isActive);
        return selectAll.getResultList();
    }

    @Override
    public CompanyEntity getLast() {
        List<CompanyEntity> list = getAll();
        int size = list.size();
        return list.get(size - 1);
    }

    public CompanyEntity getById(int id) {
        return entityManager.find(CompanyEntity.class, id);
    }

    @Override
    public int create(String name) {
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(name);
        saveCompany(newCompany);

        return newCompany.getId();
    }

    public int create(String nameToBe, String descriptionToBe) {
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(nameToBe);
        newCompany.setDescription(descriptionToBe);
        saveCompany(newCompany);

        return newCompany.getId();
    }

    @Override
    public void deleteById(int id) {
        CompanyEntity entity = entityManager.find(CompanyEntity.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    // just for your info
    public CompanyEntity getByName(String name) {
        TypedQuery<CompanyEntity> selectByName = entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.name=:comp_name", CompanyEntity.class);
        selectByName.setParameter("comp_name", name);
        return selectByName.getSingleResult();
    }

    private void saveCompany(CompanyEntity newCompany) {
        newCompany.setCreateDateTime(Timestamp.valueOf(LocalDateTime.now()));
        newCompany.setLastChangedDateTime(Timestamp.valueOf(LocalDateTime.now()));

        entityManager.getTransaction().begin();
        entityManager.persist(newCompany);
        entityManager.getTransaction().commit();
    }
}