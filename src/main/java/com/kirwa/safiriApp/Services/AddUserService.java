package com.kirwa.safiriApp.Services;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Entities.VerificationDetails;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import com.kirwa.safiriApp.Models.UserModel;
import com.kirwa.safiriApp.Repositories.UserRepository;
import com.kirwa.safiriApp.Repositories.VerificationDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddUserService {
    private static  final int EXPIRATION_TIME = 10;

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationDetailsRepository verificationDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserModel userModel) throws UserAlreadyExistsException {

        String existsEmailAddress = userModel.getEmail();
        User exists = userRepository.findByEmailAddress(existsEmailAddress);

        if (!(exists == null)) {
            throw new UserAlreadyExistsException("user with that email address already exists");
        } else {

            User user = new User();
            user.setEmailAddress(userModel.getEmail());
            user.setFirstName(userModel.getFirstName());
            user.setLastName(userModel.getLastName());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));

            User registeredUser = userRepository.save(user);
            return registeredUser;
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    public void saveVerificationDetailsForUser(String token, User user) {
        VerificationDetails verificationDetails
                = new VerificationDetails(user, token);

        verificationDetailsRepository.save(verificationDetails);
    }

    public String activateAccount(String token) {
        Optional<VerificationDetails> verificationDetailsOptional =
                Optional.ofNullable(verificationDetailsRepository.findByToken(token));

        if (verificationDetailsOptional.isPresent()) {

            VerificationDetails verificationDetails = verificationDetailsOptional.get();
            User user = verificationDetails.getUser();

            Date expirationDate = verificationDetails.getExpirationTime();
            Date currentDate = Calendar.getInstance().getTime();

            if ((expirationDate.getTime() - currentDate.getTime()) < 0) {
                verificationDetailsRepository.delete(verificationDetails);
                return "Token Already Expired";
            }
            verificationDetails.setIsEnabled(Boolean.TRUE);
            user.setEnabled(true);
            verificationDetailsRepository.save(verificationDetails);
            userRepository.save(user);

            return "Account activated Successfully";

        } else {
            return "user with that token was not found";
        }

    }

    public VerificationDetails findByToken(String token) throws IllegalAccessException {
        VerificationDetails verificationDetails = verificationDetailsRepository.findByToken(token);
        if (verificationDetails == null) {
            throw new IllegalAccessException("User with that token was not found !!!");
        }

        Integer number = new Random().nextInt(999999);
        String tokenGenerated = number.toString();

        verificationDetails.setToken(tokenGenerated);
        verificationDetails.setExpirationTime(calculateExpirationDateForUserDetails());

        verificationDetailsRepository.save(verificationDetails);
        return verificationDetails;
    }

    private Date calculateExpirationDateForUserDetails() {
        Long calendarInstance = Calendar.getInstance().getTimeInMillis();
        Long expirationDateInSeconds = calendarInstance + (EXPIRATION_TIME * 60 * 1000);
        Date expirationDate = new Date(expirationDateInSeconds);
        return expirationDate;


    }
}
