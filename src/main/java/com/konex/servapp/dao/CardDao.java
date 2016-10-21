package com.konex.servapp.dao;

import com.konex.servapp.entity.Card;
import com.konex.servapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Виталий on 13.10.2016.
 */
public interface CardDao extends JpaRepository<Card, Long> {
    List<Card> getCardByUser(User user);
}
