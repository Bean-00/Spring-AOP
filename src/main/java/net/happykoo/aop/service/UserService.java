package net.happykoo.aop.service;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.vo.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserService {

    private UserDao userDao;
    private DataSource dataSource;

    public UserService(UserDao userDao, DataSource dataSource) {
        this.userDao = userDao;
        this.dataSource = dataSource;
    }

    public void createAll(List<User> userList) throws SQLException {
        TransactionSynchronizationManager.initSynchronization();
        Connection conn = DataSourceUtils.getConnection(dataSource);
        conn.setAutoCommit(false);
        try {
            int idx = 0;
            for (User user : userList) {
                if (idx == 3) throw new RuntimeException("listInsertError");
                userDao.create(user);
                idx++;
            }
            conn.commit();
        } catch (Exception e){
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
            TransactionSynchronizationManager.unbindResource(dataSource);
            TransactionSynchronizationManager.clearSynchronization();
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
