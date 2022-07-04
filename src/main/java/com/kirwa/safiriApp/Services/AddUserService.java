package com.kirwa.safiriApp.Services;

import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddUserService {
    @Autowired
    UserRepository userRepository;


    public User addUser(User user) {
        User registeredUser= userRepository.save(user);
        return registeredUser;

    }

    public List<User> getAllUsers() {
        List<User> allUsers=userRepository.findAll();
        return allUsers;
    }
}
