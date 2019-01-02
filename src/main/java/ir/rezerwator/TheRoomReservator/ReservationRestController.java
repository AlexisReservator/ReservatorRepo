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
    @Autowired
    public ReservationRestController(ReservationDaoInterface reservationDao, RoomDaoInterface roomDao){
        this.reservationDao=reservationDao;
        this.roomDao=roomDao;
    }

    @PostMapping
    public Reservation createReservation (@Valid @RequestBody Reservation reservation, @PathVariable("roomId") int roomId) {
        Optional<Room> room=roomDao.read(roomId);
        if (!room.isPresent()) {
            throw new NotFoundException(
                    String.format("The reservation can't be created in a non-existing room."));
        }
        if (reservation.getStartDate().getTime() > reservation.getEndDate().getTime()) {
            throw new OtherException("Incorrect dates format. End date can't be earlier than start date.");
        }
        return reservationDao.create(reservation, roomId);
    }

    @GetMapping("/{reservationId}")
    public Reservation readId(@PathVariable("reservationId") int id){
        Optional<Reservation> reservation = reservationDao.read(id);
        if(!reservation.isPresent()) {
            throw new OtherException("Reservation with this id does not exist.");
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

