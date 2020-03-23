package fpt.project.clinicbackendv01.controllers;

import fpt.project.clinicbackendv01.models.Patient;
import fpt.project.clinicbackendv01.services.MapValidation;
import fpt.project.clinicbackendv01.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/patients")
@PreAuthorize("hasRole('PATIENT')")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private MapValidation mapValidation;

    @GetMapping("/{userId}")
    public ResponseEntity<?> one(@PathVariable long userId) {
        return ResponseEntity.ok()
                .body(patientService.one(userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid  @RequestBody Patient patient, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<Patient>(patientService.create(patient,principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Patient patient, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<Patient>(patientService.update(patient, principal.getName()), HttpStatus.OK);
    }
}
