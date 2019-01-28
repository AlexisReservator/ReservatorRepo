package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.model.ReservationEntity;
import ir.rezerwator.TheRoomReservator.model.RoomEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationDaoInterface {
    ReservationEntity create(ReservationEntity reservationEntity);
    Optional<ReservationEntity> readById(int id);
    List<ReservationEntity> readAll();
    ReservationEntity update(ReservationEntity reservationEntity);
    void delete (ReservationEntity reservationEntity);
    List<ReservationEntity> roomIdCompatibility(int roomId);
    Optional<ReservationEntity> readByBothIds(int id, int roomId);
    List<ReservationEntity> collidingReservations (Date startDate, Date endDate);
}
