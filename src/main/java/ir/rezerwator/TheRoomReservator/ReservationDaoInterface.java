package ir.rezerwator.TheRoomReservator;

import java.util.List;
import java.util.Optional;

public interface ReservationDaoInterface {
    Reservation create(Reservation reservation, int roomId);
    Optional<Reservation> read(int id);
    List<Reservation> readAll();
    Reservation update(Reservation reservation);
    void delete (int id);
}
