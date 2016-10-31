package com.konex.servapp.service;

import com.konex.servapp.dao.RoleDao;
import com.konex.servapp.dao.UserDao;
import com.konex.servapp.dao.UserTypeDao;
import com.konex.servapp.entity.Role;
import com.konex.servapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kneimad on 28.09.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserTypeDao userTypeDao;


    @Override
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);

        user.setUserType(userTypeDao.getOne(1L));

        userDao.save(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
        //        User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
//        if (null != user) {
//            sessionFactory.getCurrentSession().delete(user);
//        }
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> listUsers() {

        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public void editUser(Long id, User user) {
        User newUserData = userDao.getOne(id);
        newUserData.setUsername(user.getUsername());
        newUserData.setSex(user.getSex());
        newUserData.setBirthday(user.getBirthday());
        newUserData.setUserPIB(user.getUserPIB());
        newUserData.setAvatara(user.getAvatara());
        newUserData.seteMail(user.geteMail());
        newUserData.setMobile(user.getMobile());
        if(user.getPassword().length() >= 4){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //System.out.println("pass edit user ===================> " + passwordEncoder.encode(us.getPassword()));
            //System.out.println("id ===================> " + user.getId());
            newUserData.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.saveAndFlush(newUserData);
    }

    @Override
    public User finUserByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }
}

