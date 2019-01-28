package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.dto.Room;
import ir.rezerwator.TheRoomReservator.model.RoomEntity;

import java.util.List;
import java.util.Optional;

public interface RoomDaoInterface {
    RoomEntity create(RoomEntity roomEntity);
    Optional<RoomEntity> readById(int id);
    List<RoomEntity> readAll();
    RoomEntity update(RoomEntity roomEntity);
    void delete(RoomEntity roomEntity);
    Optional<RoomEntity> readByName (String name, int idOrganization);
    List<RoomEntity> OrgIdCompatibility(int idOrganization);
    Optional<RoomEntity> readByBothIds(int id, int idOrganization);
}
