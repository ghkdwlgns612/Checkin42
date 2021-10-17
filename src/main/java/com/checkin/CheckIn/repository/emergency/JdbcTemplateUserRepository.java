package com.checkin.CheckIn.repository.emergency;

import com.checkin.CheckIn.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTemplateUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        //User Parameter 세팅
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("intraId",user.getIntraId());
        parameter.put("username", user.getUsername());
        parameter.put("cardNumber", user.getCardNumber());
        parameter.put("checkIn", user.getCheckIn());
        parameter.put("checkOut", user.getCheckOut());
        //저장 후 키를 얻어와 세팅해줘야함. 안해주면 NPE발생.
        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
        user.setUserKey(key);
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query("select * from user", userRowMapper());
        return users;
    }

    @Override
    public List<User> findByCardNumberGeapo() {
        List<User> users = jdbcTemplate.query("select * from user where card_number <= ?", userRowMapper(), 1000);
        return users;
    }

    @Override
    public List<User> findByCardNumberSeocho() {
        List<User> users = jdbcTemplate.query("select * from user where card_number > ?", userRowMapper(), 1000);
        return users;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = User.builder()
                    .intraId(rs.getLong("intra_id"))
                    .username(rs.getString("username"))
                    .cardNumber(rs.getLong("card_number"))
                    .checkIn(rs.getObject("check_in", LocalDateTime.class))
                    .checkOut(rs.getObject("check_out", LocalDateTime.class))
                    .build();
            return user;
        };
    }
}
