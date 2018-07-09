package id.dhanarjkusuma.app.loket.dtoapi;

import java.util.List;
import java.util.Map;

public class ErrorResponseRequest {
    private String message;
    private Map<String, List<String>> errors;

    public ErrorResponseRequest() {
    }

    public ErrorResponseRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
