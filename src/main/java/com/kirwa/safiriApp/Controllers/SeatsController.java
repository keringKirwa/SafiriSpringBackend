package com.kirwa.safiriApp.Controllers;
import com.kirwa.safiriApp.Entities.Seats;
import com.kirwa.safiriApp.Services.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping()
public class SeatsController {
    @Autowired
    private SeatsService seatsService;

    @PostMapping("/seats/addSeat")
    public Seats addCar(@RequestBody HashMap<String, String> seatsHashMap) throws IllegalAccessException {
        Seats seatAdded =seatsService.addNewSeat(seatsHashMap);
        return seatAdded;

    }
    @GetMapping("/seats/getAllSeatsByCarId/{carId}")
    public List<Seats> getAllSeatsByCarId(@PathVariable("carId") String carId) throws IllegalAccessException {
        return seatsService.getAllSeatsByCarId(carId);
    }
}
