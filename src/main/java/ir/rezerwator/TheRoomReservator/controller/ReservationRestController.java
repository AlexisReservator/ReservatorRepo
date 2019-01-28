package ir.rezerwator.TheRoomReservator.controller;

import ir.rezerwator.TheRoomReservator.dto.Message;
import ir.rezerwator.TheRoomReservator.dto.Reservation;
import ir.rezerwator.TheRoomReservator.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/organizations/{id}/rooms/{roomId}/reservations")
@RestController
public class ReservationRestController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationRestController(ReservationService reservationService){
        this.reservationService=reservationService;
    }

    @PostMapping
    public Reservation create (@Valid @RequestBody Reservation reservation, @PathVariable("id") int idOrganization, @PathVariable("roomId") int roomId) {
        return reservationService.create(reservation, roomId, idOrganization);
    }

    @GetMapping("/{reservationId}")
    public Reservation readById(@PathVariable("reservationId") int id, @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        return reservationService.readById(id, roomId, idOrganization);
    }

    @GetMapping()
    public List<Reservation> readAll(@PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization) {
        return reservationService.readAll(roomId, idOrganization);
    }

    @PutMapping("/{reservationId}")
    public Reservation update (@PathVariable("reservationId") int id, @Valid @RequestBody Reservation reservation,
                                          @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        return reservationService.update(reservation, id, roomId, idOrganization);
    }

    @DeleteMapping("/{reservationId}")
    public Message delete (@PathVariable("reservationId") int id, @PathVariable("roomId") int roomId, @PathVariable("id") int idOrganization){
        reservationService.delete(id, roomId, idOrganization);
        return new Message("The room was successfully deleted.");
    }
}

