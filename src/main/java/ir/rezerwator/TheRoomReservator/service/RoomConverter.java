package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dto.Room;
import ir.rezerwator.TheRoomReservator.model.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomConverter {

    public Room convertToDto(RoomEntity roomEntity){
        return new Room(roomEntity.getId(), roomEntity.getName(),
                        roomEntity.getFloor(), roomEntity.getAvailability(),
                        roomEntity.getSittingSpot(), roomEntity.getStandingSpot(),
                        roomEntity.getLyingSpot(), roomEntity.getHangingSpot(),
                        roomEntity.getIdOrganization());
    }

    public RoomEntity convertToEntity(Room room){
        return new RoomEntity(room.getId(), room.getName(),
                            room.getFloor(), room.getAvailability(),
                            room.getSittingSpot(), room.getStandingSpot(),
                            room.getLyingSpot(), room.getHangingSpot(),
                            room.getIdOrganization());
    }

    public void populateEntityFields(RoomEntity roomEntity, Room room){
        roomEntity.setName(room.getName());
        roomEntity.setFloor(room.getFloor());
        roomEntity.setAvailability(room.getAvailability());
        roomEntity.setSittingSpot(room.getSittingSpot());
        roomEntity.setStandingSpot(room.getStandingSpot());
        roomEntity.setLyingSpot(room.getLyingSpot());
        roomEntity.setHangingSpot(room.getHangingSpot());
    }
}
