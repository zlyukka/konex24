package com.konex.servapp.service.reference;

import com.konex.servapp.dao.reference.GoodsRemnantsDao;
import com.konex.servapp.entity.reference.GoodsRemnants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виталий on 27.10.2016.
 */
@Service
public class GoodsRemnantsServicesImpl implements  GoodsRemnantsServices {

    @Autowired
    GoodsRemnantsDao goodsRemnantsDao;

    @Override
    public List<GoodsRemnants> gatRemnantsByPartName(String name, List tochOtbor){
        //System.out.println(tochOtbor.get(0));
        //System.out.println(tochOtbor.get(1));
        if (tochOtbor==null){
            return goodsRemnantsDao.getOstByToch(name);
        }else{
            return goodsRemnantsDao.getOstByToch(name, tochOtbor);
        }
    }
}
