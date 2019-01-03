package ir.rezerwator.TheRoomReservator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/organization/{id}/room/{roomId}/reservation")
@RestController
public class ReservationRestController {
    private final ReservationDaoInterface reservationDao;
    private final RoomDaoInterface roomDao;
    private final OrganizationDaoInterface organizationDao;
    @Autowired
    public ReservationRestController(ReservationDaoInterface reservationDao, RoomDaoInterface roomDao, OrganizationDaoInterface organizationDao){
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
            throw new OtherException("Incorrect dates format. End date can't be earlier than start date.");
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
            throw new OtherException("There is no such reservation, no such room and no such organization.");
        }
        if (!room.isPresent() && !organization.isPresent()) {
            throw new OtherException("There is no reservation under non-existing room and non-existing organization.");
        }
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing organization."));
        }
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("There is no reservation in a non-existing room."));
        }
        if(!reservation.isPresent()) {
            throw new OtherException("Reservation with this id does not exist under this room or organization.");
        }
        return reservation.get();
    }

    @GetMapping()
    public List<Reservation> readAll() {
        List<Reservation> reservations = reservationDao.readAll();
        return reservations;
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation (@PathVariable("reservationId") int id, @Valid @RequestBody Reservation reservation){
        reservation.setId(id);
        return reservationDao.update(reservation);
    }

    @DeleteMapping("/{reservationId}")
    public Message deleteReservation(@PathVariable("reservationId") int id){
        reservationDao.delete(id);
        return new Message("The room was successfully deleted.");
    }
}

