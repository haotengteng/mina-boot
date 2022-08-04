package cn.mina.boot.example.common.docker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by haoteng on 2022/7/17.
 */
@RestController
@RequestMapping("/postgres")
public class PostgresController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(path = "user")
    public String getUserInfo() {
        Integer id = 1;
        return jdbcTemplate.queryForObject("select name from user_info where id = ?", String.class, new Object[]{id});
    }

}
