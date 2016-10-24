package com.konex.servapp.service;

import com.konex.servapp.dao.CardTypeDao;
import com.konex.servapp.entity.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Виталий on 17.10.2016.
 */

@Service
public class CardTypeServicesImpl implements CardTypeServices{

    @Autowired
    private CardTypeDao cardTypeDao;



    @Override
    public void addCardType(CardType cardType) {
        cardTypeDao.save(cardType);
    }

    @Override
    public CardType getCardTypeById(Long id) {
        return cardTypeDao.getOne(id);
    }

    @Override
    public void editCardType(CardType cardType) {
        cardTypeDao.saveAndFlush(cardType);
    }

    @Override
    public void deleteCardType(Long id) {
        cardTypeDao.delete(id);
    }

    @Override
    public List<CardType> getCardType() {
        return cardTypeDao.findAll();
    }
}
