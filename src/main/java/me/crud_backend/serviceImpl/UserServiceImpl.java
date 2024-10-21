package me.crud_backend.serviceImpl;

import me.crud_backend.pojo.User;
import me.crud_backend.repository.UserRepository;
import me.crud_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        System.out.println("User details ---- " + user.toString());
        return userRepository.save(user);
    }
}
