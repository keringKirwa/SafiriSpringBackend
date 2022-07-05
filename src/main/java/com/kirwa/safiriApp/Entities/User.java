package com.kirwa.safiriApp.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Table(
            name = "USER_TABLE",
            uniqueConstraints = @UniqueConstraint(
                    name = "emailAddress_unique",
                    columnNames = "emailAddress"
            )
    )
    public class User {

        @Id
        @SequenceGenerator(
                name = "user_sequence",
                sequenceName = "student_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "student_sequence"
        )
        private Long userId;
        private String firstName;
        private String lastName;

        @Column(
                name = "emailAddress",
                nullable = false
        )
        private String emailAddress;
        private String password;
    }


