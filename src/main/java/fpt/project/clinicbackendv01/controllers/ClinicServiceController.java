package fpt.project.clinicbackendv01.controllers;


import fpt.project.clinicbackendv01.models.ClinicService;
import fpt.project.clinicbackendv01.services.ClinicServiceService;
import fpt.project.clinicbackendv01.services.MapValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class ClinicServiceController {
    @Autowired
    private ClinicServiceService clinicServices;

    @Autowired
    private MapValidation mapValidation;

    @GetMapping("/api/patient/clinicservices")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> allforPatient() {
        return new ResponseEntity<List<ClinicService>>(clinicServices.allforPatient(), HttpStatus.OK);
    }

    @GetMapping("/api/patient/clinics/{clinicId}/clinicservices")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> allByClinicId(@PathVariable long clinicId) {
        return new ResponseEntity<List<ClinicService>>(clinicServices.allByClinicId(clinicId), HttpStatus.OK);
    }

    @GetMapping("/api/clinicservices")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<?> all(Principal principal) {
        return new ResponseEntity<List<ClinicService>>(clinicServices.all(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/clinicservices/{serviceId}")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<?> one(@PathVariable long serviceId, Principal principal) {
        return new ResponseEntity<ClinicService>(clinicServices.one(serviceId, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/api/clinicservices")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<?> create(@Valid @RequestBody ClinicService clinicService, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<ClinicService>(clinicServices.create(clinicService, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/api/clinicservices")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<?> update(@Valid @RequestBody ClinicService clinicService, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<ClinicService>(clinicServices.update(clinicService, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/api/clinicservices/{id}")
    @PreAuthorize("hasRole('CLINIC')")
    public void delete(@PathVariable long id, Principal principal) {
        clinicServices.delete(id, principal.getName());
    }
}
