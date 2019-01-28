package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dao.ReservationDaoInterface;
import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.exception.exceptions.CollidingReservationException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.InputDataException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.NotFoundException;
import ir.rezerwator.TheRoomReservator.model.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationDaoInterface reservationDao;
    private final ReservationConverter reservationConverter;
    private final RoomService roomService;

    @Autowired
    public ReservationService(ReservationDaoInterface reservationDao, ReservationConverter reservationConverter,
                       RoomService roomService){
        this.reservationDao = reservationDao;
        this.reservationConverter = reservationConverter;
        this.roomService = roomService;
    }

    public Reservation create (Reservation reservation, int roomId, int idOrganization){
        roomService.readById(roomId, idOrganization);
        reservation.setRoomId(roomId);
        checkDateOrder(reservation);
        checkCollidingReservation(reservation);
        checkDuration(reservation);
        return Optional.of(reservation)
                .map(r -> reservationConverter.convertToEntity(r))
                .map(r -> reservationDao.create(r))
                .map(r -> reservationConverter.convertToDto(r))
                .get();
    }

    public Reservation readById (int id, int roomId, int idOrganization){
        roomService.readById(roomId, idOrganization);
        ReservationEntity reservationEntity = checkReservationExistence(id);
        if (!reservationEntity.getRoomId().equals(roomId)) {
            throw new NotFoundException("There is no reservation with this id under this room.");
        }
        return reservationConverter.convertToDto(reservationEntity);
    }

    public List<Reservation>  readAll (int roomId, int idOrganization){
        roomService.readById(roomId, idOrganization);
        var reservationEntities = reservationDao.roomIdCompatibility(roomId);
        if (reservationEntities.isEmpty()) {
            throw new NotFoundException("There is no reservation under this room.");
        }
        return reservationEntities
                .stream()
                .map(r -> reservationConverter.convertToDto(r))
                .collect(Collectors.toList());
    }

    public Reservation update (Reservation reservation, int id, int roomId, int idOrganization){
        roomService.readById(roomId, idOrganization);
        reservation.setRoomId(roomId);
        ReservationEntity reservationEntity = checkReservationExistence(id);
        checkReservationExistenceInRoom(id, roomId);
        checkDateOrder(reservation);
        checkCollidingReservation(reservation);
        checkDuration(reservation);
        reservationConverter.populateEntityFields(reservationEntity, reservation);
        return Optional.of(reservationDao.update(reservationEntity))
                .map(r -> reservationConverter.convertToDto(r))
                .get();
    }

    public void delete(int id, int roomId, int idOrganization){
        roomService.readById(roomId, idOrganization);
        ReservationEntity reservationEntity = checkReservationExistence(id);
        checkReservationExistenceInRoom(id, roomId);
        reservationDao.delete(reservationEntity);
    }

    private void checkReservationExistenceInRoom(int id, int roomId) {
        var idsCompatibility = reservationDao.readByBothIds(id, roomId);
        if (idsCompatibility.isEmpty()) {
            throw new NotFoundException("There is no reservation with this id under this room.");
        }
    }

    private void checkDateOrder(Reservation reservation) {
        if (reservation.getStartDate().getTime() > reservation.getEndDate().getTime()) {
            throw new InputDataException("Incorrect dates format. Start date must be earlier than end date.");
        }
    }

    private void checkCollidingReservation(Reservation reservation) {
        List<ReservationEntity> collidingReservation =
                reservationDao.collidingReservations(reservation.getStartDate(), reservation.getEndDate());
        if (!collidingReservation.isEmpty()) {
            throw new CollidingReservationException("The room with chosen date is already booked. Choose different date");
        }
    }

    private void checkDuration(Reservation reservation) {
        if (!(((reservation.getEndDate().getTime() - reservation.getStartDate().getTime()) >= 300000) &&
                ((reservation.getEndDate().getTime() - reservation.getStartDate().getTime()) <= 7200000))) {
            throw new InputDataException(
                    "Reservation duration must be equal or longer than 5 minutes and equal or shorter than 2 hours.");
        }
    }

    private ReservationEntity checkReservationExistence(int id) {
        return reservationDao.readById(id)
                .orElseThrow(() -> new NotFoundException("The reservation with this id doesn't exist"));
    }

}
