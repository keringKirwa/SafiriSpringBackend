package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.ForgotPasswordDetails;

import com.kirwa.safiriApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ForgotPasswordDetailsRepository extends
                                                 JpaRepository<ForgotPasswordDetails, Long> {
    ForgotPasswordDetails findByUser(User user);


}
