package com.konex.servapp.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Виталий on 26.10.2016.
 */
@Entity
@Table(name="Trade_points")
public class TradePoint {
    @Id
    @Column(name = "trade_pnt_is")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="MOL")
    private String mol;

    @Column(name="address")
    private String addres;

    @Column(name="update_date")
    private Date update;

    @Column(name="tel")
    private String tel;

    @Column(name="region")
    private String region;

    @Column(name="town")
    private String town;

    @Column(name="current_ip")
    private String curIp;

    @Column(name="link")
    private String link;

    public TradePoint() {
    }

    @Override
    public String toString() {
        return "TradePoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mol='" + mol + '\'' +
                ", addres='" + addres + '\'' +
                ", update=" + update +
                ", tel='" + tel + '\'' +
                ", region='" + region + '\'' +
                ", town='" + town + '\'' +
                ", curIp='" + curIp + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradePoint that = (TradePoint) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!mol.equals(that.mol)) return false;
        if (!addres.equals(that.addres)) return false;
        if (!update.equals(that.update)) return false;
        if (!tel.equals(that.tel)) return false;
        if (!region.equals(that.region)) return false;
        if (!town.equals(that.town)) return false;
        if (!curIp.equals(that.curIp)) return false;
        return link.equals(that.link);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + mol.hashCode();
        result = 31 * result + addres.hashCode();
        result = 31 * result + update.hashCode();
        result = 31 * result + tel.hashCode();
        result = 31 * result + region.hashCode();
        result = 31 * result + town.hashCode();
        result = 31 * result + curIp.hashCode();
        result = 31 * result + link.hashCode();
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMol() {
        return mol;
    }

    public void setMol(String mol) {
        this.mol = mol;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCurIp() {
        return curIp;
    }

    public void setCurIp(String curIp) {
        this.curIp = curIp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
