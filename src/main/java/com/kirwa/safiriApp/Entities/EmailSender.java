package com.kirwa.safiriApp.Entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSender {

/*FIXME: note that for us to send the email to the user , we need two classes :
   the SimpleMailMessage class
*  and the JavaMailSender class that has the method send() */

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kkirwa230@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
        System.out.println("email sent successfully ... "+ message);

    }
}
