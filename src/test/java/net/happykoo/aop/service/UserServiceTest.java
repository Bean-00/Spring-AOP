package net.happykoo.aop.service;

import net.happykoo.aop.config.ApplicationConfig;
import net.happykoo.aop.vo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService.deleteAll();
    }

    @Test
    @DisplayName("createAll 메서드 테스트 :: 예외가 발생한 경우")
    public void createAllThrowTest() {
        assertEquals(0, userService.getTotalCount());
        List<User> userList = List.of(
                User.builder().userId("ohjosama").userName("잇츠").build(),
                User.builder().userId("timetogobed").userName("아가씨").build(),
                User.builder().userId("happyKoo").userName("행복한경민이").build(),
                User.builder().userId("unHappyKoo").userName("원익보는경민이").build(),
                User.builder().userId("richKoo").userName("로또된경민이").build()
        );
        try {
            userService.createAll(userList);
            assertEquals(userList.size(), userService.getTotalCount());

        } catch (Exception e) {
            assertEquals(0, userService.getTotalCount());

        }


    }

}
