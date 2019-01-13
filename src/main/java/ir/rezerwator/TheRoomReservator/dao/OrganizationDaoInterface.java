package ir.rezerwator.TheRoomReservator.dao;

import ir.rezerwator.TheRoomReservator.dto.Organization;

import java.util.List;
import java.util.Optional;

public interface OrganizationDaoInterface {
    Organization create(Organization organization);
    Optional<Organization> read(int id);
    List<Organization> readAll();
    Organization update(Organization organization);
    void delete(int id);

}
