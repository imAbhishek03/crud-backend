package me.crud_backend.service;

import me.crud_backend.pojo.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User createUser(User user);
}
