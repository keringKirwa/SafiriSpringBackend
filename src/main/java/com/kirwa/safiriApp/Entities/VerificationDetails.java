package com.kirwa.safiriApp.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class VerificationDetails {

    private static  final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expirationTime;
    private Boolean isEnabled;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public VerificationDetails(User user, String token) {
        /*TODO: this is the constructor , called at the moment of instantiating this class.it is also in this
        *  constructor that we add the default isEnabled== false.*/

        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDateForUserDetails(EXPIRATION_TIME);
        this.isEnabled=false;
    }

    public VerificationDetails(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDateForUserDetails(EXPIRATION_TIME);
    }
    private Date calculateExpirationDateForUserDetails(int expirationTime){
        Long calendarInstance =Calendar.getInstance().getTimeInMillis();
        Long expirationDateInSeconds=calendarInstance+(EXPIRATION_TIME*60*1000);
        Date expirationDate=new Date(expirationDateInSeconds);
        return expirationDate;


    }

}
