package com.kirwa.safiriApp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"car"})
@Builder
@Table(
        name = "SEATS_TABLE",
        uniqueConstraints = @UniqueConstraint(
                name = "seats_unique",
                columnNames = "SeatId"
        )
)

public class Seats {
        /*TODO: many seats are related to one car .*/
        @Id
        @SequenceGenerator(
                name = "seats_sequenceGenerator",
                sequenceName = "seats_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "seats_sequenceGenerator"
        )
        private Long seatId;
        private Boolean available;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JsonIgnoreProperties(value = {"car"})
        @EqualsAndHashCode.Exclude
        @JoinColumn(name = "carId",
                nullable =false)

        private Car car;
}
