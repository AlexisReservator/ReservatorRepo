package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dao.OrganizationDaoInterface;
import ir.rezerwator.TheRoomReservator.dto.Organization;
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
public class OrganizationService {

    private final OrganizationDaoInterface organizationDao;
    private final OrganizationConverter organizationConverter;

    @Autowired
    public OrganizationService(OrganizationDaoInterface organizationDao,
                               OrganizationConverter organizationConverter){
        this.organizationDao = organizationDao;
        this.organizationConverter = organizationConverter;
    }

    public Organization create (Organization organization){
        organizationDao.readByName(organization.getName())
                .ifPresent(o -> {
                    throw new AlreadyExistsException("Organization with this name already exist. Choose different name.");
                });
        return Optional.of(organization)
                .map(o -> organizationConverter.convertToEntity(o))
                .map(o -> organizationDao.create(o))
                .map(o -> organizationConverter.convertToDto(o))
                .get();
    }

    public Organization readById (int id){
       return organizationDao.readById(id)
               .map(o -> organizationConverter.convertToDto(o))
               .orElseThrow(() -> new NotFoundException("Organization with this id does not exist."));
    }

    public List<Organization> readAll() {
        return organizationDao.readAll()
                .stream()
                .map(o -> organizationConverter.convertToDto(o))
                .collect(Collectors.toList());
    }

    public Organization update (Organization organization, int id){
        var organizationEntity = organizationDao.readById(id)
                .orElseThrow(() -> new NotFoundException("Organization with this id does not exist."));
        organizationDao.readByName(organization.getName())
                .filter(o -> !o.getId().equals(id))
                .ifPresent(o -> {throw new AlreadyExistsException("Organization with this name already exist. Choose different name.");});
        organizationConverter.populateEntityFields(organizationEntity, organization);
        return Optional.of(organizationDao.update(organizationEntity))
                .map(o -> organizationConverter.convertToDto(o))
                .get();
    }

    public void delete (Organization organization){
        var organizationEntity = organizationDao.readById(organization.getId())
                .orElseThrow(() -> new NotFoundException("Organization with this id does not exist."));
        organizationDao.delete(organizationEntity);
    }

}
