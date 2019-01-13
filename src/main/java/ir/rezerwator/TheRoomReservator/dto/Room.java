package ir.rezerwator.TheRoomReservator.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Room {

    @NotNull
    @Length(min=2, max=20)
    private String name;
    private int id;
    @Min(value=0)
    @Max(value=10)
    private int floor;
    private boolean availability;
    @Min(value=0)
    private int sittingSpot;
    @Min(value=0)
    private int standingSpot;
    @Min(value=0)
    private int lyingSpot;
    @Min(value=0)
    private int hangingSpot;
    @Min(value=1)
    private int idOrganization;

    public Room() {
    }

    public Room(String name, int id, int floor, boolean availability,
                int sittingSpot, int standingSpot, int lyingSpot, int hangingSpot,
                int idOrganization){
        this.name=name;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getSittingSpot() {
        return sittingSpot;
    }

    public void setSittingSpot(int sittingSpot) {
        this.sittingSpot = sittingSpot;
    }

    public int getStandingSpot() {
        return standingSpot;
    }

    public void setStandingSpot(int standingSpot) {
        this.standingSpot = standingSpot;
    }

    public int getLyingSpot() {
        return lyingSpot;
    }

    public void setLyingSpot(int lyingSpot) {
        this.lyingSpot = lyingSpot;
    }

    public int getHangingSpot() {
        return hangingSpot;
    }

    public void setHangingSpot(int lyingSpot) {
        this.hangingSpot = hangingSpot;
    }

    public int getAllSpot(){
        return sittingSpot + standingSpot + hangingSpot + lyingSpot;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }
}
