package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.ForgotPasswordDetails;

import com.kirwa.safiriApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ForgotPasswordDetailsRepository extends
                                                 JpaRepository<ForgotPasswordDetails, Long> {
    ForgotPasswordDetails findByUser(User user);


   @Query("SELECT s FROM ForgotPasswordDetails s WHERE s.token=?1")
   ForgotPasswordDetails findByToken(String token);
}