package fpt.project.clinicbackendv01.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<?> patientNotFoundException(PatientNotFoundException ex) {
        CustomExceptionResponse response = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<?> userNotFoundException(UserNotFoundException ex) {
        CustomExceptionResponse response = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingException.class)
    ResponseEntity<?> bookingException(BookingException ex) {
        CustomExceptionResponse response = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClinicException.class)
    ResponseEntity<?> clinicException(ClinicException ex) {
        CustomExceptionResponse response = new CustomExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }
}
