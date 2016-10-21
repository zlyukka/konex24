package com.konex.servapp.repository;

/**
 * Created by kneimad on 29.09.2016.
 */

import java.util.List;
import com.konex.servapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUsername(String username);
}
