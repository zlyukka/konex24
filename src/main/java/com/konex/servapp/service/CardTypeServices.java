package com.konex.servapp.service;

import com.konex.servapp.entity.CardType;

import java.util.List;

/**
 * Created by Виталий on 17.10.2016.
 */
public interface CardTypeServices {
    void addCardType(CardType cardType);
    CardType getCardTypeById(Long id);
    void editCardType(CardType cardType);
    void deleteCardType(Long id);
    List<CardType> getCardType();
}
