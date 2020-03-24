package fpt.project.clinicbackendv01.repositories;

import fpt.project.clinicbackendv01.models.Booking;
import fpt.project.clinicbackendv01.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByPatient(Patient patient);
    Optional<Booking> findByCode(String code);
}
