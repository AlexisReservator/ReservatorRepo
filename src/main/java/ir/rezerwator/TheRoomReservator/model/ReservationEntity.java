package ir.rezerwator.TheRoomReservator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class ReservationEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "reservation_sequence", strategy = "enhanced-table", parameters = {
            @org.hibernate.annotations.Parameter(name = "table_name", value = "reservation_sequence")
    })
    @GeneratedValue(generator = "table", strategy=GenerationType.TABLE)
    private Integer id;

    @NotBlank(message = "User ID can't be blanked or null.")
    @Length(min=2, max=20, message = "User ID must consist of 2 or more and less or equal to 20 signs.")
    @Column(name = "userId")
    private String userId;

    @NotNull(message = "The reservation start date can't be null, the start date needs 'yyyy-MM-dd HH:mm' format.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "startDate")
    private Date startDate;

    @NotNull(message = "The reservation end date can't be null, the end date needs 'yyyy-MM-dd HH:mm' format.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "endDate")
    private Date endDate;

    @Min(value=1)
    @Column(name = "roomId")
    private Integer roomId;

    public ReservationEntity (){

    }

    public ReservationEntity (Integer id, String userId, Date startDate, Date endDate, Integer roomId){
        this.id=id;
        this.userId=userId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.roomId=roomId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
