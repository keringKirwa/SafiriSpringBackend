package com.kirwa.safiriApp.Repositories;

import com.kirwa.safiriApp.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long > {

}
