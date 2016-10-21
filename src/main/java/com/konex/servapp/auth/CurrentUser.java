package com.konex.servapp.auth;

import com.konex.servapp.entity.User;

/**
 * Created by kneimad on 04.10.2016.
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

//    public static final String CLIENT_ROLE = "CLIENT";

    private final User user;

    public CurrentUser(User user) {

        super(user.getUsername(),
                user.getPassword(),
                user.getAuthorities());

        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

}
