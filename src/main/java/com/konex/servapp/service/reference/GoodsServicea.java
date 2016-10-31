package com.konex.servapp.service.reference;

import com.konex.servapp.entity.reference.Goods;

import java.util.List;

/**
 * Created by Виталий on 26.10.2016.
 */
public interface GoodsServicea {
    List<Goods> getGoodByPartName(String name);
}
