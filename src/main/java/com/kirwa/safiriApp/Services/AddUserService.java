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
import java.util.List;

@Service
public class AddUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationDetailsRepository verificationDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserModel userModel) throws UserAlreadyExistsException {

        String existsEmailAddress= userModel.getEmail();
        User exists=userRepository.findByEmailAddress(existsEmailAddress);

        if(! (exists ==null)){
            throw new UserAlreadyExistsException("user with that email address already exists");
        }else{

            User user = new User();
            user.setEmailAddress(userModel.getEmail());
            user.setFirstName(userModel.getFirstName());
            user.setLastName(userModel.getLastName());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));

            User registeredUser= userRepository.save(user);
            return  registeredUser;
        }
    }
    public List<User> getAllUsers() {
        List<User> allUsers=userRepository.findAll();
        return allUsers;
    }

    public void saveVerificationDetailsForUser(String token, User user) {
        VerificationDetails verificationDetails
                = new VerificationDetails(user, token);

        verificationDetailsRepository.save(verificationDetails);
    }

}
