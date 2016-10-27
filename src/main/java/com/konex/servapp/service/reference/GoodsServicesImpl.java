package com.konex.servapp.service.reference;

import com.konex.servapp.dao.reference.GoodsDao;
import com.konex.servapp.entity.reference.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
@Service
public class GoodsServicesImpl implements GoodsServicea {
    @Autowired
    GoodsDao goodsDao;

    @Override
    public List<Goods> getGoodByPartName(String name) {
        return goodsDao.getGoodsByPartName(name);
    }
}
