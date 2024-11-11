package com.hospital.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentScheduleRepository extends JpaRepository<AppointmentScheduleEntity,Long> {


    List<AppointmentScheduleEntity> findByPatientId(Long patientId);
    List<AppointmentScheduleEntity> findByPatientIdAndDoctorId(Long patientId, Long doctorId);

}
