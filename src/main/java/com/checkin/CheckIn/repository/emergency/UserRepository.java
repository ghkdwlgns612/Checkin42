package com.checkin.CheckIn.repository.emergency;

import com.checkin.CheckIn.domain.User;
import java.util.List;

public interface UserRepository {
    public void save(User user);

    public List<User> findAll();

    public List<User> findByCardNumberGeapo();

    public List<User> findByCardNumberSeocho();
}
