package ir.rezerwator.TheRoomReservator.controller;

import ir.rezerwator.TheRoomReservator.dao.OrganizationDaoInterface;
import ir.rezerwator.TheRoomReservator.dao.ReservationDaoInterface;
import ir.rezerwator.TheRoomReservator.dao.RoomDao;
import ir.rezerwator.TheRoomReservator.dto.Message;
import ir.rezerwator.TheRoomReservator.dto.Organization;
import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.dto.Room;
import ir.rezerwator.TheRoomReservator.exception.exceptions.InputDataException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/organization/{id}/room/{roomId}/reservation")
@RestController
public class ReservationRestController {
    private final ReservationDaoInterface reservationDao;
    private final RoomDao roomDao;
    private final OrganizationDaoInterface organizationDao;
    @Autowired
    public ReservationRestController(ReservationDaoInterface reservationDao, RoomDao roomDao, OrganizationDaoInterface organizationDao){
        this.reservationDao=reservationDao;
        this.roomDao=roomDao;
        this.organizationDao=organizationDao;
    }

    @PostMapping
    public Reservation createReservation (@Valid @RequestBody Reservation reservation, @PathVariable("id") int id, @PathVariable("roomId") int roomId) {
        Optional<Room> room=roomDao.read(roomId);
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("The reservation can't be created in a non-existing room."));
        }
        if (reservation.getStartDate().getTime() > reservation.getEndDate().getTime()) {
            throw new InputDataException("Incorrect dates format. End date can't be earlier than start date.");
        }
        Optional<Organization> organization=organizationDao.read(id);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The reservation can't be created in a non-existing organization."));
        }
        return reservationDao.create(reservation, id, roomId);
    }

    @GetMapping("/{reservationId}")
    public Reservation readId(@PathVariable("reservationId") int id, @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        Optional<Room> room=roomDao.read(roomId);
        Optional<Reservation> reservation = reservationDao.read(id);
        if (!room.isPresent() && !organization.isPresent() && !reservation.isPresent()) {
            throw new NotFoundException("There is no such reservation, no such room and no such organization.");
        }
        if (!room.isPresent() && !organization.isPresent()) {
            throw new NotFoundException("There is no reservation under non-existing room and non-existing organization.");
        }
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing organization."));
        }
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing room."));
        }
        return reservation.get();
    }

    @GetMapping()
    public List<Reservation> readAll(@PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization) {
        List<Reservation> reservations = reservationDao.readAll();
        Optional<Organization> organization=organizationDao.read(idOrganization);
        Optional<Room> room=roomDao.read(roomId);
        if (!room.isPresent() && !organization.isPresent()) {
            throw new NotFoundException("There is no reservation under non-existing room and non-existing organization.");
        }
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing organization."));
        }
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing room."));
        }
        if (reservations.isEmpty())
            throw new NotFoundException(
                    String.format("There is no reservations under this room."));
        return reservations;
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation (@PathVariable("reservationId") int id, @Valid @RequestBody Reservation reservation,
                                          @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        Optional<Room> room=roomDao.read(roomId);
        Optional<Reservation> reservations = reservationDao.read(id);
        if (!room.isPresent() && !organization.isPresent() && !reservations.isPresent()) {
            throw new NotFoundException("There is no such reservation, no such room and no such organization. So it can't be updated.");
        }
        if (!room.isPresent() && !organization.isPresent()) {
            throw new NotFoundException("There is no reservation under non-existing room and non-existing organization. So it can't be updated.");
        }
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing organization. So it can't be updated."));
        }
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing room. So it can't be updated."));
        }
        reservation.setId(id);
        return reservationDao.update(reservation, roomId);
    }

    @DeleteMapping("/{reservationId}")
    public Message deleteReservation(@PathVariable("reservationId") int id, @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        Optional<Room> room=roomDao.read(roomId);
        Optional<Reservation> reservation = reservationDao.read(id);
        if (!room.isPresent() && !organization.isPresent() && !reservation.isPresent()) {
            throw new NotFoundException("The non-existing reservation at non-existing room and non-existing organization can't be deleted.");
        }
        if (!room.isPresent() && !organization.isPresent()) {
            throw new NotFoundException("The reservation can't be deleted under non-existing room and non-existing organization.");
        }
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The reservation can't be deleted under non-existing organization."));
        }
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("The reservation can't be deleted under non-existing room."));
        }
        reservationDao.delete(id);
        return new Message("The room was successfully deleted.");
    }
}

