package ir.rezerwator.TheRoomReservator.dto;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

public class Organization {

    @NotNull
    @Length (min=2, max=20)
    private String name;
    private int id;

    public Organization() {
    }


    public Organization(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}