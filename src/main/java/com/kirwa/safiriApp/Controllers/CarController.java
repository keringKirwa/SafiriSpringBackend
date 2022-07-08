package com.kirwa.safiriApp.Controllers;

import com.kirwa.safiriApp.Entities.Car;
import com.kirwa.safiriApp.Services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/cars/getAllCars")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/cars/getCarById")
    public Car getCarById(@Param("carId") String carId) throws IllegalAccessException {
        Long carIdentifier = Long.parseLong(carId);
        return carService.getCarById(carIdentifier);
    }

    @PostMapping("/cars/addCar")
    public Car addCar(@RequestBody Car car) {
        log.info("Inside the addCar callBack of the DepartmentController");
        Car carAdded =carService.addNewCar(car);
        return carAdded;

    }

}
