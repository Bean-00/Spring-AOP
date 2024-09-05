package net.happykoo.aop.config;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
        dataSource.setUrl("jdbc:oracle:thin:@129.154.220.177:1521:xe");
        dataSource.setUsername("scott");
        dataSource.setPassword("tiger");


        return dataSource;
    }

    @Bean
    public UserDao userDao(DataSource dataSource) {
        return new UserDao(dataSource);
    }

    @Bean
    public UserService userService(UserDao userDao, DataSource dataSource) {
        return new UserService(userDao, dataSource);
    }
}
