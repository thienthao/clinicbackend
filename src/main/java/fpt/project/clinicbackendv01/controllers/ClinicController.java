package fpt.project.clinicbackendv01.controllers;

import fpt.project.clinicbackendv01.models.Clinic;
import fpt.project.clinicbackendv01.services.ClinicService;
import fpt.project.clinicbackendv01.services.MapValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/clinics")
@PreAuthorize("hasRole('CLINIC')")
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @Autowired
    private MapValidation mapValidation;

    @GetMapping("/{userId}")
    public ResponseEntity<?> one(@PathVariable long userId) {
        return ResponseEntity.ok()
                .body(clinicService.one(userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Clinic clinic, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<Clinic>(clinicService.create(clinic, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Clinic clinic, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<Clinic>(clinicService.update(clinic, principal.getName()), HttpStatus.OK);
    }
}
