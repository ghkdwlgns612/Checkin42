package com.checkin.CheckIn.service;

import com.checkin.CheckIn.domain.Log;
import com.checkin.CheckIn.domain.User;
import com.checkin.CheckIn.domain.enumeration.Location;
import com.checkin.CheckIn.repository.logmapper.LogMapper;
import com.checkin.CheckIn.repository.usermapper.UserMapper;
import com.checkin.CheckIn.service.dto.LocationDto;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class LogService {

    private final LogMapper logMapper;

    @Transactional
    public void checkInLog(User user) {
        String location;
        if (user.getCardNumber() >= 1000)
            location = "SEOCHO";
        else
            location = "GAEPO";
        logMapper.saveCheckIn(Log.builder()
                .cardNumber(user.getCardNumber())
                .username(user.getUsername())
                .type("CHECKIN")
                .location(location)
                .checkIn(user.getCheckIn())
                .checkOut(user.getCheckOut())
                .build());
    }

    @Transactional
    public void checkOutLog(User user) {
        String location;
        if (user.getCardNumber() >= 1000)
            location = "SEOCHO";
        else
            location = "GAEPO";
        logMapper.saveCheckOut(Log.builder()
                .cardNumber(user.getCardNumber())
                .username(user.getUsername())
                .type("CHECKOUT")
                .location(location)
                .checkIn(user.getCheckIn())
                .checkOut(user.getCheckOut())
                .build());
    }

    @Transactional
    public List<Log> gaepoLog(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return logMapper.findAllGaepo();
    }

    @Transactional
    public List<Log> seochoLog(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return logMapper.findAllSeocho();
    }

    @Transactional
    public Log lastLog() {
        return logMapper.findLastLog();
    }
}