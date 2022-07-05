package com.kirwa.safiriApp.EventListeners;

import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Events.UserRegisteredEvent;
import com.kirwa.safiriApp.Services.AddUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class UserRegisteredEventListener
        implements ApplicationListener<UserRegisteredEvent> {

    @Autowired
    private AddUserService userService;

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        Random rnd = new Random();
        Integer number = rnd.nextInt(999999);
        String token= number.toString();
        User user = event.getUser();
        userService.saveVerificationDetailsForUser(token,user);

        log.info("Token generated successfully...");
    }
}
