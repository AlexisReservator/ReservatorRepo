package ir.rezerwator.TheRoomReservator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoomDao implements RoomDaoInterface {

    private List<Room> rooms = new ArrayList<Room>() {{
        add(new Room("Redrum", 1, 10, true,
                34, 23, 100, 100,
                1));
        add(new Room("NoRoom", 2, 0, false,
                0, 0, 0, 0, 2));
    }};
    private int currentId=3;

    @Override
    public Room create (Room newRoom, int idOrganization){
        Optional<Room> room = rooms.stream()
                .filter(r -> r.getName().equals(newRoom.getName()))
                .filter(r -> r.getIdOrganization() == idOrganization)
                .findAny();
        if (room.isPresent()){
            throw new AlreadyExistsException(
                 String.format("Room with name %s already exists.", newRoom.getName()));
            }
        newRoom.setId(currentId);
        newRoom.setIdOrganization(idOrganization);
        rooms.add(newRoom);
        currentId++;
        return newRoom;
    }

    @Override
    public Optional<Room> read(int id) {
        return rooms.stream()
                .filter(room -> room.getId() ==id)
                .findFirst();
    }

    @Override
    public List<Room> readAll(int idOrganization) {
        return rooms.stream()
                .filter(room -> room.getIdOrganization() ==idOrganization)
                .collect(Collectors.toList());
    }

    @Override
    public Room update (Room room, int idOrganization) {
        Optional<Room> roomWithThisId = rooms.stream()
                .filter(r -> r.getId() == room.getId())
                .findAny();
        if (!roomWithThisId.isPresent()){
            throw new NotFoundException(
                    String.format("Room with id %d does not exist.", room.getId()));
        }
        Optional<Room> existingRoomWithNewName = rooms.stream()
                .filter(r -> r.getId() !=room.getId())
                .filter(r -> r.getName().equals(room.getName()))
                .findAny();
        if (existingRoomWithNewName.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("Room with name %s already exists.", existingRoomWithNewName.get().getName()));
        }
        roomWithThisId.get().setName(room.getName());
        roomWithThisId.get().setFloor(room.getFloor());
        roomWithThisId.get().setAvailability(room.getAvailability());
        roomWithThisId.get().setSittingSpot(room.getSittingSpot());
        roomWithThisId.get().setStandingSpot(room.getStandingSpot());
        roomWithThisId.get().setLyingSpot(room.getLyingSpot());
        roomWithThisId.get().setHangingSpot(room.getHangingSpot());
        return roomWithThisId.get();
    }

    @Override
    public void delete(int id){
        Optional<Room> roomWithThisId = rooms.stream()
                .filter(r -> r.getId() == id)
                .findAny();
        if (!roomWithThisId.isPresent()){
            throw new NotFoundException(
                    String.format("Room with id %d does not exist.", id));
        }
        rooms.remove(roomWithThisId.get());
    }

}
