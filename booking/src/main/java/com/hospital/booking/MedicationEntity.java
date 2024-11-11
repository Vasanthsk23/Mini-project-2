package com.hospital.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long patientId;

    @NotEmpty(message = "Medicine Name cannot be empty.")
    private String medicineName;

    @NotNull(message = "Number of Days cannot be empty.")
    @Min(value = 1, message = "Number of days should be greater than 0")
    private int noOfDays;

    private boolean morning;
    private boolean afterNoon;
    private boolean night;
}
