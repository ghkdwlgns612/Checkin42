package com.checkin.CheckIn.repository;

import com.checkin.CheckIn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
