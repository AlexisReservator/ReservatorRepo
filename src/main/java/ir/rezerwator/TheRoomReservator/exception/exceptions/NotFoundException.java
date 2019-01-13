package ir.rezerwator.TheRoomReservator.exception.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
