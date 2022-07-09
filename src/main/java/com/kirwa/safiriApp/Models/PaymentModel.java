package com.kirwa.safiriApp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {
    private String amount;
    private String destination;
    private String fromLocation;
    private String phoneNumberUsed;
    private String userEmailAddress;
}
