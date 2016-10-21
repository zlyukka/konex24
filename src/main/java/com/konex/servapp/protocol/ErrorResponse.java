package com.konex.servapp.protocol;

/**
 * Created by kneimad on 04.10.2016.
 */
public class ErrorResponse {
    private final String path;
    private final String message;

    public ErrorResponse(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
