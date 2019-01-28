package ir.rezerwator.TheRoomReservator.dao;


import ir.rezerwator.TheRoomReservator.model.OrganizationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class OrganizationDao implements OrganizationDaoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public OrganizationEntity create(OrganizationEntity organizationEntity){
        entityManager.persist(organizationEntity);
        return organizationEntity;
    }

    @Override
    public Optional<OrganizationEntity> readById(int id){
        return Optional.ofNullable(
                entityManager.find(OrganizationEntity.class, id));
    }

    @Override
    public List<OrganizationEntity> readAll(){
        return entityManager
                .createQuery("SELECT o FROM OrganizationEntity o", OrganizationEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public OrganizationEntity update(OrganizationEntity organizationEntity){
        return entityManager.merge(organizationEntity);
    }

    @Override
    @Transactional
    public void delete(OrganizationEntity organizationEntity){
        entityManager.remove(organizationEntity);
    }

    @Override
    @Transactional
    public Optional<OrganizationEntity> readByName (String name){
        return entityManager
                .createQuery("SELECT o FROM OrganizationEntity o WHERE o.name=:name", OrganizationEntity.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }
}
