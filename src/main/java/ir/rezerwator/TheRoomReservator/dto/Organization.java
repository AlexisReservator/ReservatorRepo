package ir.rezerwator.TheRoomReservator.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class Organization {

    private Integer id;

    @NotBlank(message = "Organization name can't be blanked or null.")
    @Length(min=2, max=20, message = "Organization name must consist of 2 or more and less or equal to 20 signs.")
    private String name;

    public Organization() {
    }

    public Organization(Integer id, String name) {
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