package com.konex.servapp.service.Good;

import com.konex.servapp.dao.reference.GoodDao;
import com.konex.servapp.entity.reference.Good;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
public interface GoodServicea {
    List<Good> getGoodByPartName(String name);
}
