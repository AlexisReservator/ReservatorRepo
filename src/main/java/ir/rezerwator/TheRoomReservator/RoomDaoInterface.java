package ir.rezerwator.TheRoomReservator;

import java.util.List;
import java.util.Optional;

public interface RoomDaoInterface {
    Room create(Room room, int idOrganization);
    Optional<Room> read(int id);
    List<Room> readAll(int idOrganization);
    Room update(Room room, int idOrganization);
    void delete(int id);
}
