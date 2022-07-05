package com.kirwa.safiriApp.Services;

import com.kirwa.safiriApp.Entities.EmailSender;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Entities.VerificationDetails;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import com.kirwa.safiriApp.Repositories.UserRepository;
import com.kirwa.safiriApp.Repositories.VerificationDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class AddUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationDetailsRepository verificationDetailsRepository;

    @Autowired
    private EmailSender emailSender;

    public User addUser(User user) throws UserAlreadyExistsException {

        /*TODO: cause an application event here, then make an application event listener
        *  to handle the same event .*/

        String emailAddress= user.getEmailAddress();
        User exists=userRepository.findByEmailAddress(emailAddress);
        if(! (exists ==null)){
            throw new UserAlreadyExistsException("user with that email address already exists");
        }else{
            User registeredUser= userRepository.save(user);
            Random rnd = new Random();
            Integer number = rnd.nextInt(999999);
            String newNumber= number.toString();

            emailSender.sendSimpleMessage(registeredUser.getEmailAddress(),
                    "You signed up with Safari2022 Booking App." +
                            " PLease Activate Your Account with this code : ",newNumber);

            return registeredUser;
        }
    }


    public List<User> getAllUsers() {
        List<User> allUsers=userRepository.findAll();
        return allUsers;
    }

    public void saveVerificationDetailsForUser(String token, User user) {
        VerificationDetails verificationToken
                = new VerificationDetails(user, token);

        verificationDetailsRepository.save(verificationToken);
    }

}
