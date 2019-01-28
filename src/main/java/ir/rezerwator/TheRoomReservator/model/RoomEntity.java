package ir.rezerwator.TheRoomReservator.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "room_sequence", strategy = "enhanced-table", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "room_sequence")
    })
    @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
    private Integer id;

    @NotBlank(message = "Room name can't be blanked or null.")
    @Length(min=2, max=20, message = "Room name must consist of 2 or more and less or equal to 20 signs.")
    @Column(name = "name")
    private String name;

    @NotNull(message = "The room floor number can't be null.")
    @Range(min=0, max=10, message = "The room floor number must be equal or bigger than 0 and equal or smaller than 10.")
    @Column(name = "floor")
    private Integer floor;

    @NotNull(message = "If the room is available sign it as true, if not sign it as false.")
    @Column(name = "availability")
    private Boolean availability;

    @NotNull(message = "The room sitting spots number can't be null.")
    @Min(value=0)
    @Column(name = "sittingSpot")
    private Integer sittingSpot;

    @NotNull(message = "The room standing spots number can't be null.")
    @Min(value=0)
    @Column(name = "standingSpot")
    private Integer standingSpot;

    @Min(value=0)
    @Column(name = "lyingSpot")
    private Integer lyingSpot;

    @Min(value=0)
    @Column(name = "hangingSpot")
    private Integer hangingSpot;

    @Min(value=1)
    @Column(name = "idOrganization")
    private Integer idOrganization;

    public RoomEntity(){

    }

    public RoomEntity(Integer id, String name, Integer floor, Boolean availability,
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

    public Integer getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(Integer idOrganization) {
        this.idOrganization = idOrganization;
    }
}

