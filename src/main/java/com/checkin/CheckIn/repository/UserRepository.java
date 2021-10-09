package com.checkin.CheckIn.repository;

import com.checkin.CheckIn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            " where u.cardNumber <= 1000")
    public List<User> findByCardNumberGeapo();

    @Query("select u from User u " +
            " where u.cardNumber > 1000")
    public List<User> findByCardNumberSeocho();

    public List<User> findAll();
}
