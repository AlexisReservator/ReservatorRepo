package ir.rezerwator.TheRoomReservator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/organization/{id}/room")
@RestController
public class RoomRestController {

    private final RoomDaoInterface roomDao;
    private final OrganizationDaoInterface organizationDao;
    @Autowired
    public RoomRestController(RoomDaoInterface roomDao, OrganizationDaoInterface organizationDao){
        this.roomDao = roomDao;
        this.organizationDao = organizationDao;
    }

    @PostMapping
    public Room createRoom (@Valid @RequestBody Room room, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The room can't be created in a non-existing organization."));
        }
        return roomDao.create(room, idOrganization);
    }

    @GetMapping("/{roomId}")
    public Room readId(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The room under non-existing organization doesn't exist."));
        }
        Optional<Room> room=roomDao.read(id);
        if (!room.isPresent()) {
            throw new NotFoundException("Room with this id does not exist.");
        }
        if (room.get().getIdOrganization() != idOrganization) {
            throw new OtherException("The room under this organization doesn't exist.");
        }
        return room.get();
    }

    @GetMapping()
    public List<Room> readAllById(@PathVariable("id") int idOrganization) {
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("There are no rooms under non-existing organization."));
        }
        List<Room> rooms=roomDao.readAll(idOrganization);
        if (rooms.isEmpty()) {
            throw new OtherException("There are no rooms under this organization.");
        }
        return rooms;
    }

    @PutMapping("/{roomId}")
    public Room updateRoom (@PathVariable("roomId") int id, @PathVariable("id") int idOrganization,
                            @Valid @RequestBody Room room){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The room under non-existing organization doesn't exist."));
        }
        Optional<Room> rooms=roomDao.read(idOrganization);
        if (rooms.get().getIdOrganization() != idOrganization) {
            throw new OtherException("The room under this organization doesn't exist.");
        }
        room.setId(id);
        return rooms.get();
    }

    @DeleteMapping("/{roomId}")
    public Message deleteRoom(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException(
                    String.format("The room under non-existing organization can't be deleted."));
        }
        Optional<Room> room=roomDao.read(id);
        if (!room.isPresent()) {
            throw new NotFoundException("Room with this id doesn't exist.");
        }
        if (room.get().getIdOrganization() != idOrganization) {
            throw new OtherException("The room under this organization doesn't exist.");
        }
        roomDao.delete(id);
        return new Message("The room was successfully deleted.");
    }
}
