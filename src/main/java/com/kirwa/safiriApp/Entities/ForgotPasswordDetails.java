package com.kirwa.safiriApp.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
public class ForgotPasswordDetails {
    private static  final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId",
            nullable =false,
            foreignKey = @ForeignKey(name = "FK_USER_FORGOT_PASSWORD_TOKEN"))
    private User user;

    public ForgotPasswordDetails(User user, String token) {

        /*TODO:As an entity, this table is created when the application boots up.
           An instance of this class created when the user sends us a forgot password request.That
           is , this constructor is called.*/

        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDateForUserDetails();
    }
    private Date calculateExpirationDateForUserDetails(){
        long calendarInstance = Calendar.getInstance().getTimeInMillis();
        return new Date(calendarInstance+(EXPIRATION_TIME*60*1000));
    }
}
