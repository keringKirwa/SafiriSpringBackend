package com.kirwa.safiriApp.EventListeners;

import com.kirwa.safiriApp.Entities.EmailSender;
import com.kirwa.safiriApp.Entities.ForgotPasswordDetails;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Events.ForgotPasswordEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

@Slf4j
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {
    @Autowired
    private EmailSender emailSender;

    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        ForgotPasswordDetails forgotPasswordDetails=event.getForgotPasswordDetails();
        User user=forgotPasswordDetails.getUser();

        emailSender.sendSimpleMessage(user.getEmailAddress(),
                "Thanks for connecting with Safari2022 Booking App." +
                        " PLease Confirm that you forgot your account password using the token : ", forgotPasswordDetails.getToken());

        log.info("Forgot password Token sent successfully successfully... =====////////////===");

    }
}
