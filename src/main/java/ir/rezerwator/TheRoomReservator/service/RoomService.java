package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dao.RoomDaoInterface;
import ir.rezerwator.TheRoomReservator.dto.Room;
import ir.rezerwator.TheRoomReservator.exception.exceptions.AlreadyExistsException;
import ir.rezerwator.TheRoomReservator.exception.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final RoomDaoInterface roomDao;
    private final RoomConverter roomConverter;
    private final OrganizationService organizationService;

    @Autowired
    public RoomService(RoomDaoInterface roomDao, RoomConverter roomConverter,
                       OrganizationService organizationService){
        this.roomDao = roomDao;
        this.roomConverter = roomConverter;
        this.organizationService = organizationService;
    }

    public Room create (Room room, int idOrganization){
        organizationService.readById(idOrganization);
        room.setIdOrganization(idOrganization);
        roomDao.readByName(room.getName(), room.getIdOrganization())
                .ifPresent(r -> {
                    throw new AlreadyExistsException("Room with this name already exist. Choose different name.");
                });
        return Optional.of(room)
                .map(r -> roomConverter.convertToEntity(r))
                .map(r -> roomDao.create(r))
                .map(r -> roomConverter.convertToDto(r))
                .get();
    }

    public Room readById (int id, int idOrganization){
        organizationService.readById(idOrganization);
        var roomEntity = roomDao.readById(id)
                .orElseThrow(() -> new NotFoundException("The room with this id doesn't exist"));
        if (!roomEntity.getIdOrganization().equals(idOrganization)) {
            throw new NotFoundException("There is no such room under this organization.");
        }
        return roomConverter.convertToDto(roomEntity);
    }

    public List<Room> readAll(int idOrganization) {
        organizationService.readById(idOrganization);
        var roomEntities = roomDao.OrgIdCompatibility(idOrganization);
        if (roomEntities.isEmpty()) {
            throw new NotFoundException("There is no room under this organization.");
        }
        return roomEntities
                .stream()
                .map(r -> roomConverter.convertToDto(r))
                .collect(Collectors.toList());
    }

    public Room update (Room room, int id, int idOrganization){
        organizationService.readById(idOrganization);
        room.setIdOrganization(idOrganization);
        var roomEntity = roomDao.readById(id)
                .orElseThrow(() -> new NotFoundException("The room with this id doesn't exist"));
        var idsCompatibility = roomDao.readByBothIds(id, idOrganization);
        if (idsCompatibility.isEmpty()) {
            throw new NotFoundException("There is no such room under this organization.");
        }
        roomDao.readByName(room.getName(), room.getIdOrganization())
                .ifPresent(o -> {throw new AlreadyExistsException("Room with this name already exist. Choose different name.");});
        roomConverter.populateEntityFields(roomEntity, room);
        return Optional.of(roomDao.update(roomEntity))
                .map(r -> roomConverter.convertToDto(r))
                .get();
    }

    public void delete(int id, int idOrganization){
        organizationService.readById(idOrganization);
        var roomEntity = roomDao.readById(id)
                .orElseThrow(() -> new NotFoundException("The room with this id doesn't exist"));
        var idsCompatibility = roomDao.readByBothIds(id, idOrganization);
        if (idsCompatibility.isEmpty()) {
            throw new NotFoundException("There is no such room under this organization.");
        }
        roomDao.delete(roomEntity);
    }
}
