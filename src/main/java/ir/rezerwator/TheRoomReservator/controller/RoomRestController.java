package ir.rezerwator.TheRoomReservator.controller;


import ir.rezerwator.TheRoomReservator.dao.OrganizationDaoInterface;
import ir.rezerwator.TheRoomReservator.dao.RoomDao;
import ir.rezerwator.TheRoomReservator.dto.Message;
import ir.rezerwator.TheRoomReservator.dto.Organization;
import ir.rezerwator.TheRoomReservator.dto.Room;
import ir.rezerwator.TheRoomReservator.exception.exceptions.InputDataException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/organization/{id}/room")
@RestController
public class RoomRestController {

    private final RoomDao roomDao;
    private final OrganizationDaoInterface organizationDao;

    @Autowired
    public RoomRestController(RoomDao roomDao, OrganizationDaoInterface organizationDao){
        this.roomDao = roomDao;
        this.organizationDao = organizationDao;
    }

    @PostMapping
    public Room createRoom (@Valid @RequestBody Room room, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException("The room can't be created in a non-existing organization.");
        }
        return roomDao.create(room, idOrganization);
    }

    @GetMapping("/{roomId}")
    public Room readId(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException("The room under non-existing organization doesn't exist.");
        }
        Optional<Room> room=roomDao.read(id);
        if (!room.isPresent()) {
            throw new NotFoundException("Room with this id does not exist.");
        }
        if (room.get().getIdOrganization() != idOrganization) {
            throw new InputDataException("The room under this organization doesn't exist.");
        }
        return room.get();
    }

    @GetMapping()
    public List<Room> readAllById(@PathVariable("id") int idOrganization) {
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException("There are no rooms under non-existing organization.");
        }
        List<Room> rooms=roomDao.readAll(idOrganization);
        if (rooms.isEmpty()) {
            throw new InputDataException("There are no rooms under this organization.");
        }
        return rooms;
    }

    @PutMapping("/{roomId}")
    public Room updateRoom (@PathVariable("roomId") int id, @PathVariable("id") int idOrganization,
                            @Valid @RequestBody Room room){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException("The room under non-existing organization doesn't exist. So it can't be updated.");
        }
        Optional<Room> rooms=roomDao.read(id);
        if (rooms.isPresent() && rooms.get().getIdOrganization() != idOrganization) {
            throw new InputDataException("The room under this organization doesn't exist. So it can't be updated.");
        }
        room.setId(id);
        return roomDao.update(room, idOrganization);
    }

    @DeleteMapping("/{roomId}")
    public Message deleteRoom(@PathVariable("roomId") int id, @PathVariable("id") int idOrganization){
        Optional<Organization> organization=organizationDao.read(idOrganization);
        if (!organization.isPresent()) {
            throw new NotFoundException("The room under non-existing organization can't be deleted.");
        }
        Optional<Room> room=roomDao.read(id);
        if (room.get().getIdOrganization() != idOrganization) {
            throw new InputDataException("The room under this organization doesn't exist. So it can't be deleted.");
        }
        roomDao.delete(id);
        return new Message("The room was successfully deleted.");
    }
}
