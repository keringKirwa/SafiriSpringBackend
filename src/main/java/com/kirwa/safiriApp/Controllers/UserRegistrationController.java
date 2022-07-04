package com.kirwa.safiriApp.Controllers;

import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import com.kirwa.safiriApp.Services.AddUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
public class UserRegistrationController {
    @Autowired
    private AddUserService userService;

    @PostMapping("/users/addUsers")
    public User addUsers(@RequestBody User user) throws
            UserAlreadyExistsException {
        log.info("Inside register Users APPLICATION of DepartmentController");
        User userRegistered = userService.addUser(user);
        String email=userRegistered.getEmailAddress();

        return userRegistered;
    }

    @GetMapping ("/users/getUsers")
    public List<User> getUsers() {
        log.info("Inside register userRegistartionService of userController");
        List<User> allUsersRegistered = userService.getAllUsers();
        return allUsersRegistered;
    }

}
