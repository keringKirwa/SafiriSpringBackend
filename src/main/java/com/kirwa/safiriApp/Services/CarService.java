package com.kirwa.safiriApp.Services;
import com.kirwa.safiriApp.Entities.Car;
import com.kirwa.safiriApp.Entities.Seats;
import com.kirwa.safiriApp.Repositories.CarRepository;
import com.kirwa.safiriApp.Repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();

    }

    public Car getCarById(Long carId) throws IllegalAccessException {
        Optional<Car> carOptional= carRepository.findById(carId);
        if(!carOptional.isPresent()){
            throw new IllegalAccessException("car With that id was not Found");
        }
        return carOptional.get();
    }
    public Car addNewCar(Car car) {
        /*TODO: once a car is saved then we also add  seats to that car*/
        Car addedCar=carRepository.save(car);
        if(!(addedCar==null)){

            for (int i =1;i<=12;i++){
                Seats seatToBeSaved =Seats.builder().car(addedCar).available(true).build();
                seatsRepository.save((seatToBeSaved));

            }

        }
        return addedCar;
    }


}
