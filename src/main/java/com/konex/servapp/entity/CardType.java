package com.konex.servapp.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Виталий on 17.10.2016.
 */
@Entity
@Table(name="Card_type")
public class CardType implements DomainObject {
    @Id
    @Column(name = "card_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_type")
    private String name;

    public CardType() {
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardType cardType = (CardType) o;

        if (!id.equals(cardType.id)) return false;
        return name.equals(cardType.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
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
}
