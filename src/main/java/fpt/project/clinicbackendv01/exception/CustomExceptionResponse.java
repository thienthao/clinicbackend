package fpt.project.clinicbackendv01.exception;

public class CustomExceptionResponse {
    private String message;

    public CustomExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
