package net.happykoo.aop.dao;

import net.happykoo.aop.config.ApplicationConfig;
import net.happykoo.aop.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationConfig.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao.deleteAll();
    }

    @Test
    @DisplayName("UserDao의 create와 findUserById 메서드 테스트 :: 정상적인 경우")
    public void findUserByIdTest() {
        User testUser = User.builder()
                .userId("its")
                .userPassword("time to")
                .userName("gotobed")
                .build();

        userDao.create(testUser);
        User user = userDao.findUserById(testUser.getUserId());

        assertEquals(testUser.getUserId(), user.getUserId());
        assertEquals(testUser.getUserName(), user.getUserName());
    }

}
