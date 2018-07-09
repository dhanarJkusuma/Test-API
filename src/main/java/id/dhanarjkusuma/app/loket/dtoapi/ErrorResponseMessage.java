package id.dhanarjkusuma.app.loket.dtoapi;

public class ErrorResponseMessage {
    private String message;

    public ErrorResponseMessage() {
    }

    public ErrorResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
