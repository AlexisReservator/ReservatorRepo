package ir.rezerwator.TheRoomReservator.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "organization")
public class OrganizationEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "organization_sequence", strategy = "enhanced-table", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "organization_sequence")
    })
    @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
    private Integer id;

    @NotBlank(message = "Organization name can't be blanked or null.")
    @Length(min=2, max=20, message = "Organization name must consist of 2 or more and less or equal to 20 signs.")
    @Column(name = "name")
    private String name;

    public OrganizationEntity(){
    }

    public OrganizationEntity(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
