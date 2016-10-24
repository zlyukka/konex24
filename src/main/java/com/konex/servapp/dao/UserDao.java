package com.konex.servapp.dao;

import com.konex.servapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByMobile(String mobile);
}
