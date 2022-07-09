package com.kirwa.safiriApp.Entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import io.github.cdimascio.dotenv.Dotenv;


public class TwilioSMSSender {
    public static void sendSMS() {
        Dotenv dotenv = null;
        dotenv = Dotenv.configure().load();

        String ACCOUNT_SID= dotenv.get("ACCOUNT_SID");
        String AUTH_TOKEN= dotenv.get("AUTH_TOKEN");
        String APP_PHONE_NUMBER= dotenv.get("TWILIO_PHONE_NUMBER");

        System.out.println(String.format(
                "Hello World. Shell is: %s. Name is: %s",
                System.getenv("SHELL"),
                AUTH_TOKEN
        ));

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+25454657595"),
                        new com.twilio.type.PhoneNumber(APP_PHONE_NUMBER),
                        "Hello , this is Safari CompanyLXTD. Thanks for" +
                                " making payment of Ksh. 1500.Continue staying with Us")
                .create();
    }
}
