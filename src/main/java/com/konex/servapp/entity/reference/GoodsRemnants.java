package com.konex.servapp.entity.reference;

import com.konex.servapp.entity.DomainObject;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Виталий on 27.10.2016.
 */
@Entity
@Table(name = "Goods_remnants")
@IdClass(GoodsRemnantsId.class)
public class GoodsRemnants implements DomainObject {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goods_id")
    private Goods goods;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trade_pnt_id")
    private TradePoint tradePoint;

    @Id
    @Column(name="serial_num")
    private int serial;

    @Column(name="incom_price")
    private float incPr;

    @Column(name="outcom_price")
    private float outPr;

//    @Column(name="update_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date update;

    @Column(name="NDS")
    private int nds;

    @Column(name="count")
    private int count;
    //----------------------------------------------


    @Override
    public String toString() {
        return "GoodsRemnants{" +
                "goods=" + goods +
                ", tradePoint=" + tradePoint +
                ", serial=" + serial +
                ", incPr=" + incPr +
                ", outPr=" + outPr +
//                ", update=" + update +
                ", nds=" + nds +
                ", count=" + count +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        GoodsRemnants that = (GoodsRemnants) o;
//
//        if (serial != that.serial) return false;
//        if (Float.compare(that.incPr, incPr) != 0) return false;
//        if (Float.compare(that.outPr, outPr) != 0) return false;
//        if (nds != that.nds) return false;
//        if (count != that.count) return false;
//        if (!goods.equals(that.goods)) return false;
//        if (!tradePoint.equals(that.tradePoint)) return false;
//        return update != null ? update.equals(that.update) : that.update == null;
//
//    }

//    @Override
//    public int hashCode() {
//        int result = goods.hashCode();
//        result = 31 * result + tradePoint.hashCode();
//        result = 31 * result + serial;
//        result = 31 * result + (incPr != +0.0f ? Float.floatToIntBits(incPr) : 0);
//        result = 31 * result + (outPr != +0.0f ? Float.floatToIntBits(outPr) : 0);
//        result = 31 * result + (update != null ? update.hashCode() : 0);
//        result = 31 * result + nds;
//        result = 31 * result + count;
//        return result;
//    }

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

    public float getIncPr() {
        return incPr;
    }

    public void setIncPr(float incPr) {
        this.incPr = incPr;
    }

    public float getOutPr() {
        return outPr;
    }

    public void setOutPr(float outPr) {
        this.outPr = outPr;
    }

//    public Date getUpdate() {
//        return update;
//    }
//
//    public void setUpdate(Date update) {
//        this.update = update;
//    }

    public int getNds() {
        return nds;
    }

    public void setNds(int nds) {
        this.nds = nds;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

