package fpt.project.clinicbackendv01.services;

import fpt.project.clinicbackendv01.exception.BookingException;
import fpt.project.clinicbackendv01.exception.PatientNotFoundException;
import fpt.project.clinicbackendv01.exception.UserNotFoundException;
import fpt.project.clinicbackendv01.models.Booking;
import fpt.project.clinicbackendv01.models.Patient;
import fpt.project.clinicbackendv01.models.Status;
import fpt.project.clinicbackendv01.models.User;
import fpt.project.clinicbackendv01.repositories.BookingRepository;
import fpt.project.clinicbackendv01.repositories.PatientRepository;
import fpt.project.clinicbackendv01.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    public List<Booking> all(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found, please create one"));
        return bookingRepository.findAllByPatient(patient);
    }

    public Booking one(long bookingId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found, please create one"));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingException("Booking not found"));
        if(!booking.getPatient().equals(patient)) {
            throw new BookingException("You don't have this booking");
        }
        return booking;
    }

    public Booking oneByCode(String bookingCode, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found, please create one"));
        Booking booking = bookingRepository.findByCode(bookingCode)
                .orElseThrow(() -> new BookingException("Booking not found"));
        if(!booking.getPatient().equals(patient)) {
            throw new BookingException("You don't have this booking");
        }
        return booking;
    }

    private String generateBookingCode() {
        Random random = new Random();
        return "DC" + random.nextInt(999);
    }

    public Booking book(Booking booking, String username) {
        try {
            if(booking.getService()==null) {
                throw new BookingException("Must have service_id and clinic_id");
            }
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + username));
            Patient patient = patientRepository.findByUser(user)
                    .orElseThrow(() -> new PatientNotFoundException("Patient profile not found, please create one"));
            booking.setCode(generateBookingCode());
            booking.setPatient(patient);
            booking.setStatus(Status.IN_PROGRESS);
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Booking fail due to: " + e.getMessage());
        }
    }

    public Booking complete(long bookingId, String username) {
        try {
            Booking booking = one(bookingId, username);
            if(booking.getStatus() == Status.IN_PROGRESS) {
                booking.setStatus(Status.COMPLETED);
            } else {
                throw new BookingException("Status is not in progress, cannot complete");
            }
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Complete Failed: " + e.getMessage());
        }
    }

    public Booking completeByCode(String bookingCode, String username) {
        try {
            Booking booking = oneByCode(bookingCode, username);
            if(booking.getStatus() == Status.IN_PROGRESS) {
                booking.setStatus(Status.COMPLETED);
            } else {
                throw new BookingException("Status is not in progress, cannot complete");
            }
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Complete Failed: " + e.getMessage());
        }
    }

    public Booking cancel(long bookingId, String username) {
        try {
            Booking booking = one(bookingId, username);
            if(booking.getStatus() == Status.IN_PROGRESS) {
                booking.setStatus(Status.CANCELED);
            } else {
                throw new BookingException("Status is not in progress, cannot cancel");
            }
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Cancel Failed: " + e.getMessage());
        }
    }

    public Booking cancelByCode(String bookingCode, String username) {
        try {
            Booking booking = oneByCode(bookingCode, username);
            if(booking.getStatus() == Status.IN_PROGRESS) {
                booking.setStatus(Status.CANCELED);
            } else {
                throw new BookingException("Status is not in progress, cannot cancel");
            }
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new BookingException("Cancel Failed: " + e.getMessage());
        }
    }
}
