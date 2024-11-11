package com.hospital.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String emailId;

    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 5,max = 300)
    private String name;

    @NotEmpty(message = "Password cannot be empty")
//    @Size(min = 5,max = 50)
    private String password;

    @NotNull(message = "Age cannot be empty")
    private int age;

    @NotEmpty(message = "Gender cannot be empty.")
    private String gender;

    @NotEmpty(message = "Number cannot be empty.")
    @Size(min=10)
    private String contactNumber;

    @NotEmpty(message = "Address cannot be empty.")
    private String address;

    private String medicalHistory;
}
