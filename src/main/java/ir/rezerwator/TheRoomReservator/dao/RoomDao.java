package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.model.RoomEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomDao implements RoomDaoInterface{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public RoomEntity create (RoomEntity roomEntity){
        entityManager.persist(roomEntity);
        return roomEntity;
    }

    @Override
    public Optional<RoomEntity> readById(int id){
        return Optional.ofNullable(
                entityManager.find(RoomEntity.class, id));
    }

    @Override
    public List<RoomEntity> readAll(){
        return entityManager
                .createQuery("SELECT r FROM RoomEntity r", RoomEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public RoomEntity update(RoomEntity roomEntity){
        return entityManager.merge(roomEntity);
    }

    @Override
    @Transactional
    public void delete(RoomEntity roomEntity){
        entityManager.remove(roomEntity);
    }

    public Optional<RoomEntity> readByName (String name, int idOrganization){
        return entityManager
                .createQuery("SELECT r FROM RoomEntity r WHERE r.name=:name AND r.idOrganization=:idOrganization", RoomEntity.class)
                .setParameter("name", name)
                .setParameter("idOrganization", idOrganization)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<RoomEntity> OrgIdCompatibility(int idOrganization){
        return entityManager
                .createQuery("SELECT r FROM RoomEntity r WHERE r.idOrganization=:idOrganization", RoomEntity.class)
                .setParameter("idOrganization", idOrganization)
                .getResultList();
    }

    @Override
    public Optional<RoomEntity> readByBothIds(int id, int idOrganization){
        return entityManager
                .createQuery("SELECT r FROM RoomEntity r WHERE r.id=:id AND r.idOrganization=:idOrganization", RoomEntity.class)
                .setParameter("id", id)
                .setParameter("idOrganization", idOrganization)
                .getResultStream()
                .findFirst();
    }
}
