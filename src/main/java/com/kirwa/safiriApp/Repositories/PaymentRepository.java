package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.PaymentDetails;
import com.kirwa.safiriApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
   @Query("SELECT s from PaymentDetails s WHERE s.user=?1")
   List<PaymentDetails> findByUserId(User user);
}
