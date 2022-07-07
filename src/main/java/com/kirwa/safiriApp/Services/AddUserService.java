package com.kirwa.safiriApp.Services;
import com.kirwa.safiriApp.Entities.ForgotPasswordDetails;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Entities.VerificationDetails;
import com.kirwa.safiriApp.Exceptions.UserAlreadyExistsException;
import com.kirwa.safiriApp.Models.ResetPasswordModel;
import com.kirwa.safiriApp.Models.UserModel;
import com.kirwa.safiriApp.Repositories.ForgotPasswordDetailsRepository;
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
    ForgotPasswordDetailsRepository forgotPasswordDetailsRepository;

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
        verificationDetails.setToken(generateTokenForUser());
        verificationDetails.setExpirationTime(calculateExpirationDateForUserDetails());

        verificationDetailsRepository.save(verificationDetails);
        return verificationDetails;
    }
    public User resetPassword(ResetPasswordModel resetPasswordModel) throws IllegalAccessException {
        Optional<User> userOptional= Optional.ofNullable(userRepository.
                findByEmailAddress(resetPasswordModel.getEmailAddress()));
        if (!userOptional.isPresent()){
            throw new IllegalAccessException("User with that email Address was not found!!");
        }else{
            User user= userOptional.get();
            if (! passwordEncoder.matches(resetPasswordModel.getOldPassword(), user.getPassword())){
                throw new IllegalAccessException("The old password provided is not correct!");
            }
            user.setPassword(passwordEncoder.encode(resetPasswordModel.getNewPassword()));
            userRepository.save(user);
            return user;
        }
    }
    public ForgotPasswordDetails forgotPassword(String emailAddress) throws IllegalAccessException {
        Optional<User> userOptional= Optional.ofNullable(userRepository.
                findByEmailAddress(emailAddress));
        if (!userOptional.isPresent()){
            throw new IllegalAccessException("User with that email Address was not found!!");
        }else{
            User user =userOptional.get();
            return saveForgotPasswordDetailsForUser(user);

        }
    }
    public ForgotPasswordDetails saveForgotPasswordDetailsForUser( User user) {
       ForgotPasswordDetails forgotPasswordDetails = new ForgotPasswordDetails();
       forgotPasswordDetails.setUser(user);
       forgotPasswordDetails.setToken(generateTokenForUser());
       forgotPasswordDetails.setExpirationTime(calculateExpirationDateForUserDetails());
       return forgotPasswordDetailsRepository.save(forgotPasswordDetails);
    }

    private Date calculateExpirationDateForUserDetails() {
        long calendarInstance = Calendar.getInstance().getTimeInMillis();
        return new Date(calendarInstance + (EXPIRATION_TIME * 60 * 1000));
    }

    private String generateTokenForUser() {
        int number = new Random().nextInt(999999);
        return Integer.toString(number);
    }

}
