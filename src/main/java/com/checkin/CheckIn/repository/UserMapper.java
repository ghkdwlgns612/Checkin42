package com.checkin.CheckIn.repository;

import com.checkin.CheckIn.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void save(User user);

    List<User> findAll();

    List<User> findByCardNumberGeapo();

    List<User> findByCardNumberSeocho();

    void deleteTestAll();

    Integer countSeochoPeople();

    Integer countGaepoPeople();

    User findByName(String username);
}