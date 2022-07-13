package com.kirwa.safiriApp.Services;

import com.kirwa.safiriApp.Entities.MpesaTransactions;
import com.kirwa.safiriApp.Entities.PaymentDetails;
import com.kirwa.safiriApp.Entities.User;
import com.kirwa.safiriApp.Models.PaymentModel;
import com.kirwa.safiriApp.Repositories.PaymentRepository;
import com.kirwa.safiriApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;
    public List<PaymentDetails> getPaymentDetails(String userId) throws IllegalAccessException {
        Long userIdentifier=Long.parseLong(userId);
        Optional<User> userOptional=userRepository.findById(userIdentifier);
        if(userOptional.isEmpty()){
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
        if (userOptional.isEmpty()){
            throw new IllegalAccessException("User with that email Address was not found ");
        }
        User user= userOptional.get();
        PaymentDetails paymentDetails=new PaymentDetails(user,paymentModel.getAmount(),
                paymentModel.getPhoneNumberUsed(),paymentModel.getFromLocation(),
                paymentModel.getDestination());
        paymentRepository.save(paymentDetails);
        return paymentDetails;

    }

    public void addPaymentViaMpesa(PaymentModel paymentModel) throws IOException {

        MpesaTransactions mpesaTransactions=new MpesaTransactions();
        String amount =paymentModel.getAmount();
        String phoneNumberToBeUsed=paymentModel.getPhoneNumberUsed();
        String response=mpesaTransactions.C2BSimulation
                ("943662", "CustomerBuyGoodsOnline",1,254793003495L,"");
        System.out.println("success payment Kirwa -----/////// = " + response);

    }
    public void stkPushSimulation()throws IOException{

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatObject = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = formatObject.format(date);

        String businessShortCode ="174379";
        String passKey= "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
        String dataToEncode=businessShortCode+passKey+strDate;
        byte[] bytes = dataToEncode.getBytes(StandardCharsets.ISO_8859_1);
        String encoded = Base64.getEncoder().encodeToString(bytes);


        MpesaTransactions mpesaTransactions=new MpesaTransactions();

        mpesaTransactions.STKPushSimulation(businessShortCode,encoded,strDate,
                "CustomerPayBillOnline","1","254793003495",
                "254793003495","174379","https://sandbox.safaricom.co.ke/mpesa/",
                "https://sandbox.safaricom.co.ke/mpesa/",
                "SafariPointLtd company","Kindly pay SafuruPoint This amount."
                );


    }

}

