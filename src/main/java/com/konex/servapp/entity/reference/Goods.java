package com.konex.servapp.entity.reference;

import com.konex.servapp.entity.DomainObject;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Виталий on 26.10.2016.
 */
@Entity
@Table(name="Goods")
public class Goods implements DomainObject {
    @Id
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "manufactor")
    private String manufactor;

    @Column(name = "ATS")
    private int ats;

    @Column(name = "registration_date")
    private Date registration;

    @Column(name = "prescription")
    private Bool prescription;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "goods")
    private Set<GoodsRemnants> Remenants;
////---------------------------------------------------
    public Goods() {
    }

    @Override
    public String toString() {
        return "reference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufactor='" + manufactor + '\'' +
//                ", ats=" + ats +
                ", prescription=" + prescription +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (ats != goods.ats) return false;
        if (!id.equals(goods.id)) return false;
        if (!name.equals(goods.name)) return false;
        if (!manufactor.equals(goods.manufactor)) return false;
        return prescription != null ? prescription.equals(goods.prescription) : goods.prescription == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + manufactor.hashCode();
        result = 31 * result + ats;
        result = 31 * result + (prescription != null ? prescription.hashCode() : 0);
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

    public String getManufactor() {
        return manufactor;
    }

    public void setManufactor(String manufactor) {
        this.manufactor = manufactor;
    }

    public int getAts() {
        return ats;
    }

    public void setAts(int ats) {
        this.ats = ats;
    }

    public Bool getPrescription() {
        return prescription;
    }

    public void setPrescription(Bool prescription) {
        this.prescription = prescription;
    }
}
