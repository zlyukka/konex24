package com.konex.servapp.service;

/**
 * Created by kneimad on 28.09.2016.
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
