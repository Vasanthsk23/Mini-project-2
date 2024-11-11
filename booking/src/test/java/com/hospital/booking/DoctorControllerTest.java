package com.hospital.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class DoctorControllerTest {

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    AppointmentScheduleRepository appointmentScheduleRepository;

    @InjectMocks
    DoctorController doctorController;

    @Test
    public void testGetAllDoctors(){
        Model model = Mockito.mock(Model.class);
        String result = doctorController.getAllDoctors(model);
        Assertions.assertEquals("book-appointment", result);
    }

    @Test
    public void testBookAppointment(){
        Model model = Mockito.mock(Model.class);
        long patientId = 669956789;
        long doctorId = 123456789;
        Mockito.when(appointmentScheduleRepository.findByPatientIdAndDoctorId(patientId, doctorId)).thenReturn(new ArrayList<>());
        Optional<DoctorEntity> obj= Mockito.mock(Optional.class);
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setNumberOfAvailableSlots(2);
        Mockito.when(obj.get()).thenReturn(doctorEntity);
        Mockito.when(doctorRepository.findById(doctorId)).thenReturn(obj);
        String result = doctorController.bookAppointment(doctorId, patientId, model);
        Assertions.assertEquals("book-appointment", result);


    }





}
