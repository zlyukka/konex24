package com.konex.servapp.protocol;

import java.util.List;
import java.util.Map;

/**
 * Created by kneimad on 04.10.2016.
 */
public class ValidationErrorResponse extends ErrorResponse {

    private Map<String, List<String>> errors;

    public ValidationErrorResponse(String url, Map<String, List<String>> errors) {
        super(url, "Validation error");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
