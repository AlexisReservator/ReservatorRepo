package ir.rezerwator.TheRoomReservator.service;

import ir.rezerwator.TheRoomReservator.dto.Organization;
import ir.rezerwator.TheRoomReservator.model.OrganizationEntity;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConverter {

    public Organization convertToDto(OrganizationEntity organizationEntity) {
        return new Organization(organizationEntity.getId(), organizationEntity.getName());
    }

    public OrganizationEntity convertToEntity(Organization organization) {
        return new OrganizationEntity(organization.getId(), organization.getName());
    }

    public void populateEntityFields(OrganizationEntity organizationEntity, Organization organization) {
        organizationEntity.setName(organization.getName());
    }
}

