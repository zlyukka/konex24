package com.konex.servapp.entity.reference;


import com.konex.servapp.entity.DomainObject;

import java.io.Serializable;

/**
 * Created by Виталий on 27.10.2016.
 */
public class GoodsRemnantsId implements Serializable{
    Goods goods;
    TradePoint tradePoint;
    int serial;
}
