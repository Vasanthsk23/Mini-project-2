package com.hospital.booking;

import jakarta.annotation.Resource;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@SpringBootTest
public class PatientControllerTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    PatientController patientController;

    @Before("")
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRegistration(){
        Model model = Mockito.mock(Model.class);
        String result = patientController.registration(model);
        Assertions.assertEquals("registration", result);
    }

@Test
    public void testAddPatient() throws NoSuchAlgorithmException, InvalidKeySpecException {
    Model model = Mockito.mock(Model.class);
    Patient patient = new Patient();
    patient.setPassword("abcd345vgv");
    BindingResult result = Mockito.mock(BindingResult.class);
    Mockito.when(patientRepository.findByEmailId(Mockito.any())).thenReturn(null);
    String result1 =  patientController.addPatient(patient,result,model);
    Assertions.assertEquals("book-appointment", result1);
}

    @Test
    public void testAddPatientError() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Model model = Mockito.mock(Model.class);
        Patient patient = new Patient();
        patient.setPassword("abcd345vgv");
        BindingResult result = Mockito.mock(BindingResult.class);
        Mockito.when(result.hasErrors()).thenReturn(true);
        String result1 =  patientController.addPatient(patient,result,model);
        Assertions.assertEquals("registration", result1);
    }

}
