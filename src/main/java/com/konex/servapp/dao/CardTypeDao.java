package com.konex.servapp.dao;

import com.konex.servapp.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Виталий on 17.10.2016.
 */
public interface CardTypeDao extends JpaRepository<CardType, Long> {
}
