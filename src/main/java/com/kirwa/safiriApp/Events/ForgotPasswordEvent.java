package com.kirwa.safiriApp.Events;

import com.kirwa.safiriApp.Entities.ForgotPasswordDetails;
import com.kirwa.safiriApp.Entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ForgotPasswordEvent extends ApplicationEvent {

    /*TODO: the object to be created will only have one object in it only , that is , User user.*/
    private final ForgotPasswordDetails forgotPasswordDetails;

    public ForgotPasswordEvent(ForgotPasswordDetails forgotPasswordDetails1) {
        super(forgotPasswordDetails1);
        this.forgotPasswordDetails=forgotPasswordDetails1;
    }
}
