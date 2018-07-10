package id.dhanarjkusuma.app.loket.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvalidCheckoutException extends RuntimeException {
    private Map<String, List<String>> fieldsError = new HashMap<>();
    public InvalidCheckoutException(String field, String s) {
        super(s);
        fieldsError.put(field, Collections.singletonList(s));
    }

    public Map<String, List<String>> getFieldsError(){
        return fieldsError;
    }
}
