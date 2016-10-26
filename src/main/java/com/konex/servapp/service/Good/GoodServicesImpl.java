package com.konex.servapp.service.Good;

import com.konex.servapp.dao.reference.GoodDao;
import com.konex.servapp.entity.reference.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
@Service
public class GoodServicesImpl implements GoodServicea{
    @Autowired
    GoodDao goodDao;

    @Override
    public List<Good> getGoodByPartName(String name) {
        return goodDao.getGoodByPartName(name);
    }
}
