package ir.rezerwator.TheRoomReservator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrganizationDao implements OrganizationDaoInterface {

    private List<Organization> organizations = new ArrayList<Organization>() {{
        add(new Organization("Skra Belchatow", 1));
        add(new Organization("Stocznia Szczecin", 2));
    }};
    private int currentId = 3;

    @Override
    public Organization create(Organization newOrganization) {
        Optional<Organization> organization = organizations.stream()
                .filter(o -> o.getName().equals(newOrganization.getName()))
                .findAny();
        if (organization.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("Organization with name %s already exists.", newOrganization.getName()));
        }
        newOrganization.setId(currentId);
        organizations.add(newOrganization);
        currentId++;
        return newOrganization;
    }

    @Override
    public Optional<Organization> read(int id) {
        return organizations.stream()
                .filter(organization -> organization.getId() == id)
                .findFirst();
    }

    @Override
    public List<Organization> readAll() {
        return organizations;
    }

    @Override
    public Organization update (Organization organization){
        Optional<Organization> organizationWithThisId = organizations.stream()
                .filter(o -> o.getId() == organization.getId())
                .findAny();
        if (!organizationWithThisId.isPresent()) {
            throw new NotFoundException(
                    String.format("Organization with id %d does not exist.", organization.getId()));
        }
        Optional<Organization> existingOrganizationWithNewName = organizations.stream()
                .filter(o -> o.getId() != organization.getId())
                .filter(o -> o.getName().equals(organization.getName()))
                .findAny();
        if (existingOrganizationWithNewName.isPresent()) {
            throw new AlreadyExistsException(
                    String.format("Organization with name %s already exists."
                            , existingOrganizationWithNewName.get().getName()));
        }
        organizationWithThisId.get().setName(organization.getName());
        return organizationWithThisId.get();
    }

    @Override
    public void delete(int id){
        Optional<Organization> organizationWithThisId = organizations.stream()
                .filter(o -> o.getId() == id)
                .findAny();
        if (!organizationWithThisId.isPresent()) {
            throw new NotFoundException(
                    String.format("Organization with id %d does not exist.", id));
        }
        organizations.remove(organizationWithThisId.get());
    }

}
