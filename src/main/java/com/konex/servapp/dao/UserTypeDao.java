package com.konex.servapp.dao;

import com.konex.servapp.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kneimad on 09.09.2016.
 */
public interface UserTypeDao extends JpaRepository<UserType, Long> {
}
