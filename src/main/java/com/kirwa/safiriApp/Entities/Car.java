package com.kirwa.safiriApp.Entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"seatsList"})
@Table(
        name = "CARS",
        uniqueConstraints = @UniqueConstraint(
                name = "carNumberPlate_unique",
                columnNames = "carNumberPlate"
        )
)
public class Car {

    @Id
    @SequenceGenerator(
            name = "car_sequenceGenerator",
            sequenceName = "car_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_sequenceGenerator"
    )
    private Long carId;

    @Column(
            name = "carNumberPlate",
            nullable = false
    )
    private String carNumberPlate;
    private String fromLocation;
    private String destination;

    @Column(nullable = true)
    private int  noOfSeatsAvailable;
    private String dateOfTravel;

    @OneToMany(mappedBy="car", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"car"})
    @EqualsAndHashCode.Exclude
    private List<Seats> seatsList;

    public Car(String carNumberPlate, String from, String destination,String dateOfTravel) {
        super();
        this.carNumberPlate = carNumberPlate;
        this.dateOfTravel=dateOfTravel;
        this.fromLocation=from;
        this.destination=destination;

    }
}
