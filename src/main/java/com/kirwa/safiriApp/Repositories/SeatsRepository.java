package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.Car;
import com.kirwa.safiriApp.Entities.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsRepository extends JpaRepository<Seats,Long> {

   @Query("SELECT s FROM Seats s WHERE s.car= ?1")
   List<Seats> findByCar(Car car);
}
