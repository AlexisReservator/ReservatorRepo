package ir.rezerwator.TheRoomReservator;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
