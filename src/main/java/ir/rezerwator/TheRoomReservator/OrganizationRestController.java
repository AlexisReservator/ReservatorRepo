package ir.rezerwator.TheRoomReservator;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping ("/organization")
@RestController
public class OrganizationRestController {

    private final OrganizationDaoInterface organizationDao;

    @Autowired
    public OrganizationRestController(OrganizationDaoInterface organizationDao) {
        this.organizationDao = organizationDao;
    }


    @PostMapping
    public Organization createOrganization (@Valid @RequestBody Organization organization){
        return organizationDao.create(organization);
    }

    @GetMapping("/{id}")
    public Organization readId(@PathVariable("id") int id) {
        Optional<Organization> organization = organizationDao.read(id);
        if (!organization.isPresent()) {
            throw new NotFoundException ("Organization with this id does not exist.");
        }
        return organization.get();
    }

    @GetMapping()
    public List<Organization> readAll() {
        List<Organization> organizations = organizationDao.readAll();
        return organizations;
    }

    @PutMapping("/{id}")
    public Organization updateName (@PathVariable("id") int id, @Valid @RequestBody Organization organization){
        organization.setId(id);
        return organizationDao.update(organization);
    }

    @DeleteMapping("/{id}")
    public Message deleteOrganization(@PathVariable("id") int id){
        organizationDao.delete(id);
        return new Message("The organization was successfully deleted.");
    }
}
