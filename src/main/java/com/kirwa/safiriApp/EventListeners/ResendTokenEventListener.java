package com.kirwa.safiriApp.EventListeners;

import com.kirwa.safiriApp.Entities.EmailSender;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Events.ResendTokenEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResendTokenEventListener implements ApplicationListener<ResendTokenEvent> {
    @Autowired
    private EmailSender emailSender;
    @Override
    public void onApplicationEvent(ResendTokenEvent event) {
        User user = event.getVerificationDetails().getUser();
        String token =event.getVerificationDetails().getToken();

        emailSender.sendSimpleMessage(user.getEmailAddress(),
                "Thanks for connecting with Safari2022 Booking App." +
                        " Activate Your account with this token : ",token);

        log.info("Token generated successfully..."+token);

    }
}
