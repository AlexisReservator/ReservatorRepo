package ir.rezerwator.TheRoomReservator;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Message {
    @JsonFormat
    private String information;

    public Message(String information){
        this.information=information;
    }
}
