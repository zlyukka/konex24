package com.konex.servapp.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by kneimad on 28.09.2016.
 */
@Entity
@Table(name = "Users_type")
public class UserType implements DomainObject{

    @Id
    @Column(name = "user_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_type_name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userType")
    private Set<User> userSet;

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

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
}
