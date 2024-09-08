package net.happykoo.aop.config;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.handler.TxAdvice;
import net.happykoo.aop.handler.TxHandler;
import net.happykoo.aop.service.UserSercviceTx;
import net.happykoo.aop.service.UserService;
import net.happykoo.aop.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
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
    public ProxyFactoryBean userService(UserDao userDao, PlatformTransactionManager transactionManager) {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();


        UserService target = new UserServiceImpl(userDao);
        factoryBean.setTarget(target);
        factoryBean.setInterceptorNames("txAdvisor");
        return factoryBean;
    }

    @Bean
    public NameMatchMethodPointcut txPointcut(){
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("createAll");

        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor txAdvisor(TxAdvice txAdvice,
                                            NameMatchMethodPointcut txPointcut) {
       DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();

       advisor.setAdvice(txAdvice);
       advisor.setPointcut(txPointcut);


       return advisor;
    }

    @Bean
    public TxAdvice txAdvice(PlatformTransactionManager transactionManager) {
        return new TxAdvice(transactionManager);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
