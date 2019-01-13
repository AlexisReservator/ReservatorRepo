package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.dto.Room;

import java.util.List;
import java.util.Optional;

public interface RoomDao {
    Room create(Room room, int idOrganization);
    Optional<Room> read(int id);
    List<Room> readAll(int idOrganization);
    Room update(Room room, int idOrganization);
    void delete(int id);
}
