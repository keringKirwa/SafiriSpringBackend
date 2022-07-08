package com.kirwa.safiriApp.Services;
import com.kirwa.safiriApp.Entities.Car;
import com.kirwa.safiriApp.Entities.Seats;
import com.kirwa.safiriApp.Repositories.CarRepository;
import com.kirwa.safiriApp.Repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SeatsService {
    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    CarRepository carRepository;


    public Seats addNewSeat(HashMap<String , String> seatsMap) throws IllegalAccessException {
        Optional<Car> carOptional=carRepository.findById(Long.valueOf(seatsMap.get("carId")));
        if(!carOptional.isPresent()){
            throw new IllegalAccessException("the car for this seat was not found ");
        }
        Seats seats1 =Seats.builder().car(carOptional.get()).available(true).build();
        seatsRepository.save((seats1));
        return seats1;
    }

    public List<Seats> getAllSeatsByCarId(String carId) throws IllegalAccessException {
        Long carIdentifier = Long.parseLong(carId);
        Optional<Car> carOptional =carRepository.findById(carIdentifier);
        if(!carOptional.isPresent()){
            throw new IllegalAccessException("car with that id was not found !!!");
        }
        Car car=carOptional.get();
        List<Seats> seatsListAvailable=seatsRepository.findByCar(car);

        return  seatsListAvailable;

    }
}
