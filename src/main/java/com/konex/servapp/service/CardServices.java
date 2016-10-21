package com.konex.servapp.service;

import com.konex.servapp.entity.Card;
import com.konex.servapp.entity.User;

import java.security.Principal;
import java.util.List;

/**
 * Created by Виталий on 13.10.2016.
 */
public interface CardServices {
    void addCard(Card myCard);

    void editCard(Card curCard, Principal currentUser);

    void deleteCard(long id);

    List<Card> getCardsByUser(User user);

    Card getCardById(Long id);

    List<Card> getCards();
}
