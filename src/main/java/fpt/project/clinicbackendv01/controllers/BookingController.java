package fpt.project.clinicbackendv01.controllers;

import fpt.project.clinicbackendv01.models.Booking;
import fpt.project.clinicbackendv01.services.BookingService;
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
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private MapValidation mapValidation;

    @GetMapping("/api/patient/bookings")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> all(Principal principal) {
        return new ResponseEntity<List<Booking>>(bookingService.all(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/patient/bookings/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> one(@PathVariable long bookingId, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.one(bookingId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/v01/patient/bookings/{bookingCode}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> oneByCode(@PathVariable String bookingCode, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.oneByCode(bookingCode, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/api/patient/bookings")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> book(@Valid @RequestBody Booking booking, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidation.map(result);
        if(errorMap != null) return errorMap;
        return new ResponseEntity<Booking>(bookingService.book(booking, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/api/v01/patient/bookings/{bookingCode}/complete")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> completeByCode(@PathVariable String bookingCode, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.completeByCode(bookingCode, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/api/patient/bookings/{bookingId}/complete")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> complete(@PathVariable long bookingId, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.complete(bookingId, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/api/v01/patient/bookings/{bookingCode}/cancel")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> cancelByCode(@PathVariable String bookingCode, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.cancelByCode(bookingCode, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/api/patient/bookings/{bookingId}/cancel")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> cancel(@PathVariable long bookingId, Principal principal) {
        return new ResponseEntity<Booking>(bookingService.cancel(bookingId, principal.getName()), HttpStatus.OK);
    }
}
