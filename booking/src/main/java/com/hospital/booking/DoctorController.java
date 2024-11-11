package com.hospital.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Doctor", description = "Doctor schedule management APIs")
@Controller
public class DoctorController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentScheduleRepository appointmentScheduleRepository;

    @Operation(
            summary = "Retrieve all doctor's schedule",
            description = "Get all doctor's schedule. The response is html.",
            tags = { "schedules", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = DoctorEntity.class), mediaType = "application/html") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/get-all-doctors")
    public String getAllDoctors(Model model){
        model.addAttribute("doctorsList", doctorRepository.findAll());
        return "book-appointment";
    }

    @Operation(
            summary = "Books an appointment with given doctor id and patient id",
            description = "Books an appointment with given doctor id and patient id. The response is html.",
            tags = { "schedule", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = DoctorEntity.class), mediaType = "application/html") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/bookAppointment/{id}/{patientId}")
    public String bookAppointment(@PathVariable(value = "id") long doctorId, @PathVariable(value = "patientId") long patientId, Model model){

        if(appointmentScheduleRepository.findByPatientIdAndDoctorId(patientId, doctorId).isEmpty()){
            // update number of available slots in doctor table
            DoctorEntity doctorEntity = doctorRepository.findById(doctorId).get();
            doctorEntity.setNumberOfAvailableSlots(doctorEntity.getNumberOfAvailableSlots() - 1);
            doctorRepository.save(doctorEntity);
            //Save schedule details in DB
            AppointmentScheduleEntity appointmentScheduleEntity = new AppointmentScheduleEntity();
            appointmentScheduleEntity.setDoctorId(doctorId);
            appointmentScheduleEntity.setPatientId(patientId);
            appointmentScheduleEntity.setScheduleTiming(doctorEntity.getConsultationTiming());
            appointmentScheduleEntity.setDoctorName(doctorEntity.getDoctorName());
            appointmentScheduleEntity.setSpeciality(doctorEntity.getSpeciality());
            appointmentScheduleRepository.save(appointmentScheduleEntity);
            model.addAttribute("displaySuccessAlert", true);
        }
        else {
            model.addAttribute("bookedAlert", true);
        }

        model.addAttribute("doctorsList", doctorRepository.findAll());

        model.addAttribute("patientId", patientId);
        model.addAttribute("id", doctorId);
        return "book-appointment";
    }
}
