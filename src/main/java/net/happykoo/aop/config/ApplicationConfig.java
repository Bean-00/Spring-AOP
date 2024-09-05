package net.happykoo.aop.config;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.handler.TxHandler;
import net.happykoo.aop.service.UserSercviceTx;
import net.happykoo.aop.service.UserService;
import net.happykoo.aop.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

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
    public UserService userService(UserDao userDao, PlatformTransactionManager transactionManager) {
        UserService target = new UserServiceImpl(userDao);
        return (UserService) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[] {UserService.class},
                new TxHandler(transactionManager, target, "createAll"));
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
