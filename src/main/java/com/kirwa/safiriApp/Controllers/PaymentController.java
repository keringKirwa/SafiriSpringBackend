package com.kirwa.safiriApp.Controllers;

import com.kirwa.safiriApp.Entities.PaymentDetails;
import com.kirwa.safiriApp.Models.PaymentModel;
import com.kirwa.safiriApp.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping()
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getPaymentDetails/{userId}")
    public List<PaymentDetails> getPaymentDetailsForUser(@PathVariable("userId") String userId)
            throws IllegalAccessException {
       return paymentService.getPaymentDetails(userId);
    }

    @PostMapping("/addPayment")
    public PaymentDetails addPaymentDetailsForUser(@RequestBody PaymentModel paymentModel)
            throws IllegalAccessException {
       return paymentService.addPaymentForUser(paymentModel);
    }

    @PostMapping("/invokeMpesa")
    public void addPaymentViaMpesa(@RequestBody PaymentModel paymentModel)
            throws IllegalAccessException, IOException {
       paymentService.addPaymentViaMpesa(paymentModel);
    }

    @GetMapping("/invokeMpesa")
    public void invokeStkPush() throws IllegalAccessException, IOException {
        paymentService.stkPushSimulation();
    }
}
