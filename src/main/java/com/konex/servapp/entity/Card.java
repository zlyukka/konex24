package com.konex.servapp.entity;



import javax.persistence.*;
import java.util.Date;

/**
 * Created by Виталий on 13.10.2016.
 */
@Entity
@Table(name="Cards")
public class Card implements DomainObject {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_number")
    private Long cardNom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "card_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_type")
    private CardType cardType;

    @Column(name = "activate_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activate;

    @Column(name = "deactivate_date")
    private Date deactivate;

    @Column(name = "comments")
    private String comments;

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardNom() {
        return cardNom;
    }

    public void setCardNom(Long cardNom) {
        this.cardNom = cardNom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getActivate() {
        return activate;
    }

    public void setActivate(Date activate) {
        this.activate = activate;
    }

    public Date getDeactivate() {
        return deactivate;
    }

    public void setDeactivate(Date deactivate) {
        this.deactivate = deactivate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNom=" + cardNom +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", cardType=" + cardType +
                ", activate=" + activate +
                ", deactivate=" + deactivate +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!id.equals(card.id)) return false;
        if (!cardNom.equals(card.cardNom)) return false;
        if (user != null ? !user.equals(card.user) : card.user != null) return false;
        if (!name.equals(card.name)) return false;
        if (!cardType.equals(card.cardType)) return false;
        if (activate != null ? !activate.equals(card.activate) : card.activate != null) return false;
        if (deactivate != null ? !deactivate.equals(card.deactivate) : card.deactivate != null) return false;
        return comments != null ? comments.equals(card.comments) : card.comments == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cardNom.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + cardType.hashCode();
        result = 31 * result + (activate != null ? activate.hashCode() : 0);
        result = 31 * result + (deactivate != null ? deactivate.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}

