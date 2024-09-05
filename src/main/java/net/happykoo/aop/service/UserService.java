package net.happykoo.aop.service;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.vo.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserService {

    private final UserDao userDao;
    private final PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    public void createAll(List<User> userList) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            int idx = 0;
            for (User user : userList) {
                if (idx == 3) throw new RuntimeException("listInsertError");
                userDao.create(user);
                idx++;
            }
            transactionManager.commit(status);
        } catch (Exception e){
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    public void create(User user) {
        userDao.create(user);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }

    public int getTotalCount() {
        return userDao.getTotalCount();
    }
}
