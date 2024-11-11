package com.hospital.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
    List<MedicationEntity> findByPatientId(long patientId);
}
