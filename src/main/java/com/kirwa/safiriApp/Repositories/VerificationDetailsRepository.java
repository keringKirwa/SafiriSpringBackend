package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.VerificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationDetailsRepository extends JpaRepository<VerificationDetails, Long> {
    VerificationDetails findByToken(String token);


    void delete(VerificationDetails verificationDetails);
}
