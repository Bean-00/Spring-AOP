package net.happykoo.aop.service;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.vo.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;


    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createAll(List<User> userList) {
        int idx = 0;
        for (User user : userList) {
            if (idx == 3) throw new RuntimeException("listInsertError");
            userDao.create(user);
            idx++;
        }
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public int getTotalCount() {
        return userDao.getTotalCount();
    }
}
