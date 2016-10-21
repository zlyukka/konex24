package com.konex.servapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kneimad on 28.09.2016.
 */

@Entity
@Table(name = "Users")
public class User implements DomainObject, UserDetails{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "login", unique = true)
//    @NotBlank
    @Size(min = 4, max = 32, message = "ПОМИЛКА! Логін повинен бути від 4 до 32 символів")
    private String username;

    @Column(name = "password")
//    @NotBlank
    @Size(min = 4, message = "ПОМИЛКА! Пароль повинен бути від 4 символів")
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name = "user_PIB")
//    @NotBlank
    @Size(min = 4, max = 150, message = "ПОМИЛКА! ПІБ повинен бути більше 4 символів")
    private String userPIB;

    @Column(name = "email", unique = true)
//    @NotBlank
    @Email
    private String eMail;

    @Column(name = "mobile", unique = true)
//    @NotBlank
    @Size(min = 8, max = 14, message = "ПОМИЛКА! Телефон повинен бути від 8 до 14 символів")
    private String mobile;

    @Column(name = "birthday")
//    @NotNull
    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "avatara")
    private byte[] avatara;

    @Column(name = "reg_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTime;

    @PrePersist
    protected void onCreate() {
        regTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        regTime = new Date();
    }

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "updated")
    private Boolean updated;

    @Column(name = "rating")
    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_type")
    private UserType userType;

    @ManyToMany
    @JoinTable(name = "Users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

//    protected User() {}
//
//    public User(String username, String userPIB){
//        this.username = username;
//        this.userPIB = userPIB;
//    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userPIB='" + userPIB + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : this.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUserPIB() {       return userPIB;    }

    public void setUserPIB(String userPIB) {         this.userPIB = userPIB;    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public byte[] getAvatara() {
        return avatara;
    }

    public void setAvatara(byte[] avatara) {
        this.avatara = avatara;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getCurrentUserId() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return u.getId();
    }

    public static boolean isAnonymous() {
        // Метод SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        // нічого не дасть, оскільки анонімний користувач теж вважається авторизованим
        return "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
