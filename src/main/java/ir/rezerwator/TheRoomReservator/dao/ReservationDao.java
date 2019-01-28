package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.model.ReservationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDao implements ReservationDaoInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ReservationEntity create(ReservationEntity reservationEntity) {
        entityManager.persist(reservationEntity);
        return reservationEntity;
    }

    @Override
    public Optional<ReservationEntity> readById(int id) {
        return Optional.ofNullable(
                entityManager.find(ReservationEntity.class, id));
    }

    @Override
    public List<ReservationEntity> readAll() {
        return entityManager
                .createQuery("SELECT r FROM ReservationEntity r", ReservationEntity.class)
                .getResultList();
    }

    @Override
    @Transactional
    public ReservationEntity update(ReservationEntity reservationEntity) {
        return entityManager.merge(reservationEntity);
    }

    @Override
    @Transactional
    public void delete(ReservationEntity reservationEntity) {
        entityManager.remove(reservationEntity);
    }

    @Override
    public List<ReservationEntity> roomIdCompatibility(int roomId) {
        return entityManager
                .createQuery("SELECT r FROM ReservationEntity r WHERE r.roomId=:roomId", ReservationEntity.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    @Override
    public Optional<ReservationEntity> readByBothIds(int id, int roomId) {
        return entityManager
                .createQuery("SELECT r FROM ReservationEntity r WHERE r.id=:id AND r.roomId=:roomId", ReservationEntity.class)
                .setParameter("id", id)
                .setParameter("roomId", roomId)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<ReservationEntity> collidingReservations(Date startDate, Date endDate) {
        return entityManager
                .createQuery("SELECT r "
                        + "FROM ReservationEntity r "
                        + "WHERE ("
                        + "r.startDate>=:endDate "
                        + "AND r.endDate<:startDate"
                        + ") OR ("
                        + "r.startDate<=:endDate "
                        + "AND r.endDate>:startDate"
                        + ")", ReservationEntity.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}