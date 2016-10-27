package com.konex.servapp.dao.reference;

import com.konex.servapp.entity.reference.TradePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
public interface TradePointDao  extends JpaRepository<TradePoint, Long> {
}
