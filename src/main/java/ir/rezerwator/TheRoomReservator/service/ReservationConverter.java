package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.model.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public Reservation convertToDto(ReservationEntity reservationEntity){
        return new Reservation(reservationEntity.getId(), reservationEntity.getUserId(),
                            reservationEntity.getStartDate(), reservationEntity.getEndDate(),
                            reservationEntity.getRoomId());
    }

    public ReservationEntity convertToEntity(Reservation reservation){
        return new ReservationEntity(reservation.getId(), reservation.getUserId(),
                                    reservation.getStartDate(), reservation.getEndDate(),
                                    reservation.getRoomId());
    }

    public void populateEntityFields(ReservationEntity reservationEntity, Reservation reservation){
        reservationEntity.setUserId(reservation.getUserId());
        reservationEntity.setStartDate(reservation.getStartDate());
        reservationEntity.setEndDate(reservation.getEndDate());
    }
}
