package com.kirwa.safiriApp.Controllers;

import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Entities.VerificationDetails;
import com.kirwa.safiriApp.Events.ResendTokenEvent;
import com.kirwa.safiriApp.Events.UserRegisteredEvent;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import com.kirwa.safiriApp.Models.TokenModel;
import com.kirwa.safiriApp.Models.UserModel;
import com.kirwa.safiriApp.Services.AddUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
public class UserRegistrationController {
    @Autowired
    private AddUserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/users/addUsers")
    public User addUsers(@RequestBody UserModel userModel) throws UserAlreadyExistsException {
        log.info("Inside register Users APPLICATION of DepartmentController");
        User userRegistered = userService.registerUser(userModel);
        publisher.publishEvent(new UserRegisteredEvent(userRegistered));
        return  userRegistered;
    }

    @GetMapping ("/users/getUsers")
    public List<User> getUsers() {
        log.info("Inside register userRegistration Service of userController");
        List<User> allUsersRegistered = userService.getAllUsers();
        return allUsersRegistered;
    }

    @PostMapping("/users/activateAccount")
    public String activateAccount(@RequestBody TokenModel token)  {
        log.info("Inside register Users APPLICATION of DepartmentController");
        String activated = userService.activateAccount(token.getToken());

        return  activated;
    }

    @PostMapping("/users/resendToken/{oldToken}")
    public String resendToken(@PathVariable("oldToken") String token) throws IllegalAccessException {
        /*FIXME: the user has to send us the old token for a new on e to be generated*/
        log.info("Inside send new token controller-------//////////-----"+token);
        VerificationDetails oldVerificationDetails = userService.findByToken(token);
        publisher.publishEvent(new ResendTokenEvent(oldVerificationDetails));

        return  "token sent via the email :"+oldVerificationDetails.getUser().getEmailAddress();
    }

}
