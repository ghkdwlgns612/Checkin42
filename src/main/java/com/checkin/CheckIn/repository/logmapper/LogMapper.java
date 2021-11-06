package com.checkin.CheckIn.repository.logmapper;

import com.checkin.CheckIn.domain.Log;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LogMapper {
    void saveCheckIn(Log log);

    void saveCheckOut(Log log);

    Optional<Log> findByName(String username);

    List<Log> findAll();

    List<Log> findAllGaepo();

    List<Log> findAllSeocho();

    Log findLastLog();
}

