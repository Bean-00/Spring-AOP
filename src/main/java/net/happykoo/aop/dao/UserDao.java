package net.happykoo.aop.dao;

import net.happykoo.aop.vo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User findUserById(String userId) {
        RowMapper<User> rowMapper = (rs, rowNum) -> User.builder()
                .userId(rs.getString("user_id"))
                .userName(rs.getString("user_name"))
                .build();
        return jdbcTemplate.queryForObject("SELECT user_id, user_name FROM user_test WHERE user_id = ?", rowMapper, userId);
    }

    public void create(User user) {
        jdbcTemplate.update("INSERT INTO user_test(user_id, user_password, user_name) VALUES (?, ?, ?)",
                user.getUserId(),
                user.getUserPassword(),
                user.getUserName());
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM user_test");
    }

    public int getTotalCount() {
        RowMapper<Integer> rowMapper = (rs, rowNum) -> rs.getInt("count");
        return jdbcTemplate.queryForObject("SELECT COUNT(*) AS count FROM user_test", rowMapper);
    }


}
