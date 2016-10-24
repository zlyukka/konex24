package com.konex.servapp.service;

import com.konex.servapp.dao.CardDao;
import com.konex.servapp.entity.Card;
import com.konex.servapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Created by Виталий on 13.10.2016.
 */
@Service
public class CardServicesImpl implements CardServices {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private UserService userService;


    @Override
    public void addCard(Card myCard) {
        cardDao.save(myCard);
    }

    @Override
    public void editCard(Card curCard) {
        cardDao.saveAndFlush(curCard);
    }

    @Override
    public void deleteCard(long id) {
        cardDao.delete(id);
    }


    @Override
    public List<Card> getCardsByUser(User user) {
        return cardDao.getCardByUser(user);
    }

    @Override
    public Card getCardById(Long id){
        User user =  userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user.getUserPIB());
        if (user.getAuthorities().toString().contains("ROLE_ADMIN")){
            return cardDao.getOne(id);
        }
        for(Card card : getCardsByUser(user)) {
            if (card.getId() == id) {
                return cardDao.getOne(id);
            }
        }
        return null;
    }

    @Override
    public List<Card> getCards() {
        return cardDao.findAll();
    }


}
