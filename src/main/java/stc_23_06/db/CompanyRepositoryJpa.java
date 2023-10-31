package stc_23_06.db;

import io.qameta.allure.Allure;
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
        Allure.step("ORM получение списка всех актуальных компаний");
        TypedQuery<CompanyEntity> selectAll = entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.deletedAt is not null", CompanyEntity.class);
        return selectAll.getResultList();
    }

    @Override
    public List<CompanyEntity> getAll(boolean isActive) {
        Allure.step("ORM получение списка всех актуальных компаний с фильтрацией");
        Allure.parameter("active=", isActive);
        TypedQuery<CompanyEntity> selectAll = entityManager.createQuery("SELECT c FROM CompanyEntity c WHERE c.deletedAt is null AND c.isActive=:active", CompanyEntity.class);
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
        Allure.step("ORM получение компании по ИД");
        Allure.parameter("id=", id);
        return entityManager.find(CompanyEntity.class, id);
    }

    @Override
    public int create(String name) {
        Allure.step("ORM добавление новой компании");
        Allure.parameter("company=", name);
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(name);
        saveCompany(newCompany);
        return newCompany.getId();
    }

    @Override
    public CompanyEntity create_(String nameToBe) {
        Allure.step("ORM добавление новой компании");
        Allure.parameter("company=", nameToBe);
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(nameToBe);
        saveCompany(newCompany);
        return newCompany;
    }

    @Override
    public void deleteById(int id) {
        Allure.step("ORM удаление компании по ИД");
        Allure.parameter("id=", id);
        CompanyEntity entity = entityManager.find(CompanyEntity.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void refresh(CompanyEntity company) {
        entityManager.refresh(company);
    }

    @Override
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