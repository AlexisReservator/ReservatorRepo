package ir.rezerwator.TheRoomReservator.dto;

public class ErrorDetails {
    private String errorMessage;

    public ErrorDetails(String errorMessage){
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
