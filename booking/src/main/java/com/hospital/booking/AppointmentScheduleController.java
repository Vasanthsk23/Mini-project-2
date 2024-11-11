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

@Tag(name = "Appointment", description = "Patient schedule management APIs")
@Controller
public class AppointmentScheduleController {
    @Autowired
    AppointmentScheduleRepository appointmentScheduleRepository;



    @Operation(
            summary = "Gets all schedule for given patient id",
            description = "Gets all schedule for given patient id. The response is html.",
            tags = { "get Patient schedule", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/view-schedules/{id}")
    public String viewSchedule(Model model, @PathVariable(value = "id") Long patientId){
        model.addAttribute("scheduleList", appointmentScheduleRepository.findByPatientId(patientId));
        return "my-schedule";
    }
}
