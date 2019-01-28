package ir.rezerwator.TheRoomReservator.controller;


import ir.rezerwator.TheRoomReservator.dto.Message;
import ir.rezerwator.TheRoomReservator.dto.Organization;
import ir.rezerwator.TheRoomReservator.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping ("/organizations")
@RestController
public class OrganizationRestController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationRestController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PostMapping
    public Organization create(@Valid @RequestBody Organization organization){
       return organizationService.create(organization);
    }

    @GetMapping("/{id}")
    public Organization readById(@PathVariable("id") int id) {
        return organizationService.readById(id);
    }

    @GetMapping()
    public List<Organization> readAll() {
        return organizationService.readAll();
    }

    @PutMapping("/{id}")
    public Organization update(@PathVariable("id") int id, @Valid @RequestBody Organization organization){
        return organizationService.update(organization, id);
    }


    @DeleteMapping("/{id}")
    public Message delete(@PathVariable("id") int id, Organization organization){
        organizationService.delete(organization);
        return new Message("The organization was successfully deleted.");
    }
}







