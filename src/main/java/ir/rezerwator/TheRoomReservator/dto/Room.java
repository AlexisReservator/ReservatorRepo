package ir.rezerwator.TheRoomReservator.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Optional;

import static java.lang.Integer.sum;

public class Room {

    private Integer id;

    @NotBlank(message = "Room name can't be blanked or null.")
    @Length(min=2, max=20, message = "Room name must consist of 2 or more and less or equal to 20 signs.")
    private String name;

    @NotNull(message = "The room floor number can't be null.")
    @Range(min=0, max=10, message = "The room floor number must be equal or bigger than 0 and equal or smaller than 10.")
    private Integer floor;

    @NotNull(message = "If the room is available sign it as true, if not sign it as false.")
    private Boolean availability;

    @NotNull(message = "The room sitting spots number can't be null.")
    @Min(value=0)
    private Integer sittingSpot;

    @NotNull(message = "The room standing spots number can't be null.")
    @Min(value=0)
    private Integer standingSpot;

    @Min(value=0)
    private Integer lyingSpot;

    @Min(value=0)
    private Integer hangingSpot;

    @Min(value=1)
    private Integer idOrganization;

    public Room() {
    }

    public Room(Integer id, String name, Integer floor, Boolean availability,
                Integer sittingSpot, Integer standingSpot, Integer lyingSpot, Integer hangingSpot, Integer idOrganization){
        this.id=id;
        this.name=name;
        this.floor=floor;
        this.availability=availability;
        this.sittingSpot=sittingSpot;
        this.standingSpot=standingSpot;
        this.lyingSpot=lyingSpot;
        this.hangingSpot=hangingSpot;
        this.idOrganization=idOrganization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Integer getSittingSpot() {
        return sittingSpot;
    }

    public void setSittingSpot(Integer sittingSpot) {
        this.sittingSpot = sittingSpot;
    }

    public Integer getStandingSpot() {
        return standingSpot;
    }

    public void setStandingSpot(Integer standingSpot) {
        this.standingSpot = standingSpot;
    }

    public Integer getLyingSpot() {
        return lyingSpot;
    }

    public void setLyingSpot(Integer lyingSpot) {
        this.lyingSpot = lyingSpot;
    }

    public Integer getHangingSpot() {
        return hangingSpot;
    }

    public void setHangingSpot(Integer hangingSpot) {
        this.hangingSpot = hangingSpot;
    }

    public int getAllSpot(){

        return sittingSpot + standingSpot + Optional.ofNullable(hangingSpot).orElse(0) + Optional.ofNullable(lyingSpot).orElse(0);
    }

    public Integer getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Integer idOrganization) {
        this.idOrganization = idOrganization;
    }
}
