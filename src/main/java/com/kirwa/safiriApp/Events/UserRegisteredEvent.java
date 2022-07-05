package com.kirwa.safiriApp.Events;

import com.kirwa.safiriApp.Entities.User;
import org.springframework.context.ApplicationEvent;
import lombok.Getter;
import lombok.Setter;

/*FIXME: this is the class that will be instantiated in order to get the ApplicationEvent
   that will be published. */

@Getter
@Setter
public class UserRegisteredEvent extends ApplicationEvent {

    private final User user;

    public UserRegisteredEvent(User user) {
        super(user);
        this.user=user;
    }
}