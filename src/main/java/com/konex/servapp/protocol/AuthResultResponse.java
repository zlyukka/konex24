package com.konex.servapp.protocol;

/**
 * Created by kneimad on 04.10.2016.
 */
public class AuthResultResponse {
    private final long id;

    public AuthResultResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}