package com.kirwa.safiriApp.Services;

import com.kirwa.safiriApp.Entities.PaymentDetails;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Models.PaymentModel;
import com.kirwa.safiriApp.Repositories.PaymentRepository;
import com.kirwa.safiriApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;
    public List<PaymentDetails> getPaymentDetails(String userId) throws IllegalAccessException {
        Long userIdentifier=Long.parseLong(userId);
        Optional<User> userOptional=userRepository.findById(userIdentifier);
        if(!userOptional.isPresent()){
            throw new IllegalAccessException("user with that is was not found !!");

        }
        List<PaymentDetails> paymentDetailsList=paymentRepository.findByUserId(userOptional.get());
        if (paymentDetailsList.size() == 0){
            throw new IllegalAccessException("No payment details for that user was found !!!");
        }
        return paymentDetailsList;
    }

    public PaymentDetails addPaymentForUser(PaymentModel paymentModel) throws IllegalAccessException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.
                findByEmailAddress(paymentModel.getUserEmailAddress()));
        if (!userOptional.isPresent()){
            throw new IllegalAccessException("User with that email Address was not found ");
        }
        User user= userOptional.get();
        PaymentDetails paymentDetails=new PaymentDetails(user,paymentModel.getAmount(),
                paymentModel.getPhoneNumberUsed(),paymentModel.getFromLocation(),
                paymentModel.getDestination());
        paymentRepository.save(paymentDetails);
        return paymentDetails;

    }
}

