package com.kirwa.safiriApp.EventListeners;

import com.kirwa.safiriApp.Entities.EmailSender;
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

    @Autowired
    private EmailSender emailSender;

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        /*FIXME: when the object of the class UserRegistered is published then the 
           onApplicationEvent method is triggered. this method will generate a random string and then
            save the details of activation in the database by calling the service layer again.*/

        Random rnd = new Random();
        Integer number = rnd.nextInt(999999);
        String token= number.toString();

        User user = event.getUser();
        userService.saveVerificationDetailsForUser(token,user);

        emailSender.sendSimpleMessage(user.getEmailAddress(),
                "You signed up with Safari2022 Booking App." +
                        " PLease Activate Your Account with this code : ",token);

        log.info("Token generated successfully...");
    }
}
