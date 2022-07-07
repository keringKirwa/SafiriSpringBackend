package com.kirwa.safiriApp.Events;

import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Entities.VerificationDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResendTokenEvent extends ApplicationEvent {
    private final VerificationDetails verificationDetails;

    public ResendTokenEvent(VerificationDetails verificationDetails) {
        /*TODO: this constructor is used in creating an event, that event has
           verificationDetails in it. and the VerificationDetails has got
           User instance in it too*/

        super(verificationDetails);
        this.verificationDetails=verificationDetails;
    }
}
