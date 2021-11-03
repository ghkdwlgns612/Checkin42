package com.checkin.CheckIn.repository.logmapper;

import com.checkin.CheckIn.domain.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void save(Log log);
}

