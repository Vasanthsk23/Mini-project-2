package com.hospital.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Tag(name = "Patient", description = "Patient management APIs")
@Controller
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("/")
    public String registration(Model model){
        model.addAttribute("patient", new Patient());
        return "registration";
    }

    @Operation(
            summary = "Register new patient",
            description = "Register new patient The response is book appointment  html.",
            tags = { "register patient", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Patient.class), mediaType = "application/html") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/register")
    public String addPatient(@ModelAttribute("patient") @Valid Patient patient, BindingResult result, Model model) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (result.hasErrors()){
            return "registration";
        }else {
            Patient patientResult = patientRepository.findByEmailId(patient.getEmailId());
            if(patientResult == null){
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                KeySpec spec = new PBEKeySpec(patient.getPassword().toCharArray(), salt, 65536, 128);
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] encodedPassword = factory.generateSecret(spec).getEncoded();
                patient.setPassword(Arrays.toString(encodedPassword));
                patientRepository.save(patient);
                model.addAttribute("patientId", patient.getId());
            }
            else{
                model.addAttribute("patientId", patientResult.getId());
            }
            model.addAttribute("doctorsList", doctorRepository.findAll());
            return "book-appointment";
        }
    }
    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() {
        final byte[] keyValue = new byte[] { 'T', 'E', 'S', 'T' };
        return new SecretKeySpec(keyValue, "AES");
    }
}
