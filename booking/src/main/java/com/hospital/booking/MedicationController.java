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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Medication", description = "Medication management APIs")
@Controller
public class MedicationController {

    @Autowired
    MedicationRepository medicationRepository;

    @Operation(
            summary = "Add new medication",
            description = "Add new medication to prescription. The response is book appointment  html.",
            tags = { "add medication", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MedicationEntity.class), mediaType = "application/html") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/add-new-medication/{id}")
    public String addNewMedication(@ModelAttribute("newmedication") @Valid MedicationEntity medicationEntity, BindingResult result, Model model, @PathVariable(value = "id") long patientId){
        if(!result.hasErrors()){
            medicationEntity.setPatientId(patientId);
            medicationRepository.save(medicationEntity);
            MedicationEntity newmedicationEntity =  new MedicationEntity();
            model.addAttribute("newmedication", newmedicationEntity);
        }else{
            model.addAttribute("newmedication", medicationEntity);
        }
        model.addAttribute("patientId", patientId);
        model.addAttribute("medicationList", medicationRepository.findByPatientId(patientId));
        return "medication";
    }

    @Operation(
            summary = "Retrieve all medication for a given patient id",
            description = "Get patient's prescription. The response is html.",
            tags = { "medications", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MedicationEntity.class), mediaType = "application/html") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/view-medications/{patientId}")
    public String viewMedications(Model model, @PathVariable(value = "patientId") long patientId){
        model.addAttribute("medicationList", medicationRepository.findByPatientId(patientId));
        MedicationEntity medicationEntity =  new MedicationEntity();
        model.addAttribute("newmedication", medicationEntity);
        model.addAttribute("patientId", patientId);
        return "medication";
    }
}
