package com.hospital.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest
public class AppointmentScheduleControllerTest {
    @Mock
    AppointmentScheduleRepository appointmentScheduleRepository;

    @InjectMocks
    AppointmentScheduleController appointmentScheduleController;

    @Test
    public void testViewSchedule(){
        Model model = Mockito.mock(Model.class);
        long patientId = 3456789;
        String result = appointmentScheduleController.viewSchedule(model, patientId);
        Assertions.assertEquals("my-schedule", result);

    }
}
