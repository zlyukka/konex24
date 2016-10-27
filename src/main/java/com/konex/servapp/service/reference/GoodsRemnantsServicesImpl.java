package com.konex.servapp.service.reference;

import com.konex.servapp.dao.reference.GoodsRemnantsDao;
import com.konex.servapp.entity.reference.GoodsRemnants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Виталий on 27.10.2016.
 */
@Service
public class GoodsRemnantsServicesImpl implements  GoodsRemnantsServices {

    @Autowired
    GoodsRemnantsDao goodsRemnantsDao;

    @Override
    public List<GoodsRemnants> gatRemnantsByPartName(String name) {
        return goodsRemnantsDao.getOstByToch(name);
    }
}
