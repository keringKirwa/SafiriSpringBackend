package com.kirwa.safiriApp.Entities;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PaymentDetails {

    /*TODO: Once payment is made , we send our users a message to the phone number used
    *  for making payment.Every payment has a  user associated with it.*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String amount;
    private Date paymentDate;
    private String destination;
    private String fromLocation;
    private String phoneNumberUsed;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_PAYMENT_DETAILS"))
    @EqualsAndHashCode.Exclude
    private User user;


    public PaymentDetails(User user,String amountPaid,String phoneNumberUsed,
                          String fromLocation, String destination) {

        super();

        this.amount=amountPaid;
        this.phoneNumberUsed=phoneNumberUsed;
        this.fromLocation=fromLocation;
        this.destination=destination;
        this.paymentDate=calculatePaymentDate();
        this.user=user;
    }

    private Date calculatePaymentDate(){
       return (Calendar.getInstance().getTime());



    }

}
