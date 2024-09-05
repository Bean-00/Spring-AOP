package net.happykoo.aop.service;

import net.happykoo.aop.dao.UserDao;
import net.happykoo.aop.vo.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserSercviceTx implements UserService {
    private final PlatformTransactionManager transactionManager;
    private final UserService userService;

    public UserSercviceTx(PlatformTransactionManager transactionManager, UserService userService) {
        this.userService = userService;
        this.transactionManager = transactionManager;
    }
    @Override
    public void createAll(List<User> userList) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userService.createAll(userList);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        userService.create(user);
    }

    @Override
    public void deleteAll() {
        userService.deleteAll();
    }

    @Override
    public int getTotalCount() {
        return userService.getTotalCount();
    }
}
