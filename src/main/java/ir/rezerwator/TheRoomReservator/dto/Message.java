package ir.rezerwator.TheRoomReservator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Message {
    private String information;

    public Message(String information){
        this.information=information;
    }


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
