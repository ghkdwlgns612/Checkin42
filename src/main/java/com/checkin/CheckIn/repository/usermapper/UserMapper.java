package com.checkin.CheckIn.repository.usermapper;

import com.checkin.CheckIn.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    void save(User user);

    List<User> findAll();

    List<User> findByCardNumberGeapo();

    List<User> findByCardNumberSeocho();

    void deleteTestAll();

    Integer countSeochoPeople();

    Integer countGaepoPeople();

    Optional<User> findByName(String username);

    void updateByCardNumber(Integer cardNumber, String username);

    void updateCheckInByCardNumber(Integer cardNumber, String username);

    void updateCheckOutByCardNumber(Integer cardNumber, String username);

    void deleteByName(String username);
}