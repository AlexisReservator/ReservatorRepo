package ir.rezerwator.TheRoomReservator;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class ReservationDao implements ReservationDaoInterface {

    private List<Reservation> reservations = new ArrayList<Reservation>() {{
        add(new Reservation(1, 1,"piesek", Date.from(Instant.parse("2019-01-04T15:00:00.000Z")),
                Date.from(Instant.parse("2019-01-04T18:00:00.000Z"))));
        add(new Reservation(2, 2, "kotek", Date.from(Instant.parse("2019-01-05T15:00:00.000Z")),
                Date.from(Instant.parse("2019-01-05T18:00:00.000Z"))));
    }};
    private int currentId =3;

    @Override
    public Reservation create(Reservation newReservation, int id, int roomId) {
        List<Reservation> collidingReservations = reservations.stream()
            .filter(r -> r.getRoomId() == roomId)
            .filter(r -> (r.getStartDate().getTime() > newReservation.getEndDate().getTime() &&
                    r.getEndDate().getTime() < newReservation.getStartDate().getTime()) ||
                    (r.getStartDate().getTime() < newReservation.getEndDate().getTime() &&
                    r.getEndDate().getTime() > newReservation.getStartDate().getTime()))
            .collect(Collectors.toList());
        if (!collidingReservations.isEmpty()) {
            throw new AlreadyExistsException(
                    String.format("The room with chosen date is already booked. Choose different date. Colliding reservations: %s",
                            collidingReservations.toString())
            );
        }
        newReservation.setId(currentId);
        newReservation.setRoomId(roomId);
        reservations.add(newReservation);
        currentId++;
        return newReservation;
    }

    @Override
    public Optional<Reservation> read(int id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst();
    }

    @Override
    public List<Reservation> readAll() {
        return reservations;
    }

    @Override
    public Reservation update (Reservation reservation) {
        Optional<Reservation> reservationWithThisId = reservations.stream()
                .filter(r ->r.getId() == reservation.getId())
                .findAny();
        if (!reservationWithThisId.isPresent()){
            throw new NotFoundException(
                    String.format("Reservation with id %d does not exist.", reservation.getId()));
        }
        List<Reservation> collidingReservations = reservations.stream()
                .filter(r -> r.getRoomId() == reservation.getRoomId())
                .filter(r -> r.getStartDate().getTime() >= reservation.getEndDate().getTime() ||
                        r.getEndDate().getTime() <= reservation.getStartDate().getTime())
                .collect(Collectors.toList());
        if (!collidingReservations.isEmpty()) {
            throw new AlreadyExistsException(
                    String.format("Room with chosen date is already booked; choose different date; colliding reservations: %s.",
                            collidingReservations.toString())
            );
        }
        reservationWithThisId.get().setRoomId(reservation.getRoomId());
        reservationWithThisId.get().setUserId(reservation.getUserId());
        reservationWithThisId.get().setStartDate(reservation.getStartDate());
        reservationWithThisId.get().setEndDate(reservation.getEndDate());
        return reservationWithThisId.get();
    }

    @Override
    public void delete(int id){
        Optional<Reservation> reservationWithThisId = reservations.stream()
                .filter(r -> r.getId() ==id)
                .findAny();
        if(!reservationWithThisId.isPresent()){
            throw new NotFoundException(
                    String.format("Reservation with id %d does not exist.", id));
        }
        reservations.remove(reservationWithThisId.get());
    }

}
