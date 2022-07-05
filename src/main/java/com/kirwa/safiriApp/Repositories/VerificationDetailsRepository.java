package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.VerificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationDetailsRepository extends
                                               JpaRepository<VerificationDetails, Long> {
    VerificationDetails findByToken(String token);
}
