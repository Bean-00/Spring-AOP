package net.happykoo.aop.service;

import net.happykoo.aop.vo.User;

import java.util.List;

public interface UserService {
    void createAll(List<User> userList);
    void create(User user);
    void deleteAll();
    int getTotalCount();
}
