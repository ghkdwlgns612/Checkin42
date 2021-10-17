package com.checkin.CheckIn.repository;

import com.checkin.CheckIn.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    @Autowired
    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = em.createQuery("select u from User u", User.class).getResultList();
        return users;
    }

    @Override
    public List<User> findByCardNumberGeapo() {
        List<User> usersGeapo = em.createQuery("select u from User u where u.cardNumber <= 1000").getResultList();
        return usersGeapo;
    }

    @Override
    public List<User> findByCardNumberSeocho() {
        List<User> usersSeocho = em.createQuery("select u from User u where u.cardNumber > 1000").getResultList();
        return usersSeocho;
    }
}
