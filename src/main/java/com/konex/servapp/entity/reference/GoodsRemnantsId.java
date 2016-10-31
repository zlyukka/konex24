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

    ///--------------

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public TradePoint getTradePoint() {
        return tradePoint;
    }

    public void setTradePoint(TradePoint tradePoint) {
        this.tradePoint = tradePoint;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }



    @Override
    public int hashCode() {
        int result = goods.hashCode();
        result = 31 * result + tradePoint.hashCode();
        result = 31 * result + serial;
        return result;
    }
}
