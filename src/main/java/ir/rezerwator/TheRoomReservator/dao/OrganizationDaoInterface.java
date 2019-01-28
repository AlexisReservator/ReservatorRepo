package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.model.OrganizationEntity;

import java.util.List;
import java.util.Optional;

public interface OrganizationDaoInterface {
    OrganizationEntity create(OrganizationEntity organizationEntity);
    Optional<OrganizationEntity> readById (int id);
    List<OrganizationEntity> readAll();
    OrganizationEntity update(OrganizationEntity organizationEntity);
    void delete(OrganizationEntity organizationEntity);
    Optional<OrganizationEntity> readByName (String name);

}
